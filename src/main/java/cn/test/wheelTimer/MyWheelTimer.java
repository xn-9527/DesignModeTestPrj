package cn.test.wheelTimer;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by chay on 2019/11/2.
 */
@Slf4j
public class MyWheelTimer {
    private static final int MAX_TICKS_PER_WHEEL = 1073741824;
    private static final long WAITING_SHUTDOWN_MILLIS = 1000L;
    private static final boolean IS_WINDOWS;

    static {
        String os = System.getProperty("os.name");
        // windows
        IS_WINDOWS = os.toLowerCase().contains("win");
    }

    /**
     * 时间轮id，整个系统全局唯一
     */
    private static final AtomicInteger AUTO_ID = new AtomicInteger();
    /**
     * 对象的id
     */
    private int id;
    /**
     * 线程池
     */
    private ExecutorService executorService;
    /**
     * 任务缓存:
     * key : 格子编号
     * value： 该格子内的任务，自动按照触发时间升序排序
     */
    private final ConcurrentHashMap<Integer, PriorityQueue<MyWheelWorker>> taskWheel;
    /**
     * 一圈的格数，2的次方
     */
    private Integer ticksPerWheel;
    /**
     * 一格时间
     */
    private long tickDuration;
    /**
     * 一圈时间
     */
    private long roundDuration;
    /**
     * 时间单位
     */
    private TimeUnit unit;
    /**
     * 时间轮的主线程
     */
    private final ExecutorService workerThread = Executors.newSingleThreadExecutor();
    private final Worker worker = new Worker();
    /**
     * 当前转到的格子数
     */
    private int tick;
    /**
     * 时间轮开始时间
     */
    private volatile long startTime;
    private final CountDownLatch startTimeInitialized = new CountDownLatch(1);
    /**
     * 时间轮状态
     */
    private static final int WORKER_STATE_INIT = 0;
    private static final int WORKER_STATE_STARTED = 1;
    private static final int WORKER_STATE_SHUTDOWN = 2;
    @SuppressWarnings({"unused", "FieldMayBeFinal", "RedundantFieldInitialization"})
    /**
     * 0 - init, 1 - started, 2 - shut down
     */
    private volatile int workerState = WORKER_STATE_INIT;

    public MyWheelTimer(ExecutorService executorService,
                        long tickDuration, TimeUnit unit, int ticksPerWheel) {
        AUTO_ID.incrementAndGet();
        this.id = AUTO_ID.get();
        if (executorService == null) {
            throw new NullPointerException("executorService");
        }

        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (tickDuration <= 0) {
            throw new IllegalArgumentException(
                    "tickDuration must be greater than 0: " + tickDuration);
        }
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException(
                    "ticksPerWheel must be greater than 0: " + ticksPerWheel);
        }
        if (tickDuration > Long.MAX_VALUE / ticksPerWheel ) {
            throw new IllegalArgumentException(
                    "roundDuration = tickDuration * ticksPerWheel too big");
        }
        this.roundDuration = tickDuration * ticksPerWheel;

        this.executorService = executorService;

        // Normalize ticksPerWheel to power of two and initialize the wheel.
        this.ticksPerWheel = ticksPerWheel;
        taskWheel = createWheel(ticksPerWheel);

        // Convert tickDuration to Millis.
        this.unit = unit;
        this.tickDuration = unit.toMillis(tickDuration);
    }

    /**
     * 根据轮子格子数，近似到2的N次方，然后初始化每个格子的任务队列
     *
     * @param ticksPerWheel
     * @return
     */
    @SuppressWarnings("unchecked")
    private static ConcurrentHashMap<Integer, PriorityQueue<MyWheelWorker>> createWheel(int ticksPerWheel) {
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException(
                    "ticksPerWheel must be greater than 0: " + ticksPerWheel);
        }
        if (ticksPerWheel > MAX_TICKS_PER_WHEEL) {
            throw new IllegalArgumentException(
                    "ticksPerWheel may not be greater than 2^30: " + ticksPerWheel);
        }

        ticksPerWheel = normalizeTicksPerWheel(ticksPerWheel);
        ConcurrentHashMap<Integer, PriorityQueue<MyWheelWorker>> wheel = new ConcurrentHashMap<Integer, PriorityQueue<MyWheelWorker>>(ticksPerWheel);
        for (int i = 0; i < ticksPerWheel; i++) {
            wheel.put(i, new PriorityQueue<>());
        }
        return wheel;
    }

    /**
     * 启动时间轮
     */
    public void start() {
        log.info("wheel {} started", id);
        switch (workerState) {
            case WORKER_STATE_INIT:
                workerThread.submit(worker);
                workerState = WORKER_STATE_STARTED;
                break;
            case WORKER_STATE_STARTED:
                break;
            case WORKER_STATE_SHUTDOWN:
                throw new IllegalStateException("cannot be started once stopped");
            default:
                throw new Error("Invalid WorkerState");
        }

        // Wait until the startTime is initialized by the worker.
        while (startTime == 0) {
            try {
                startTimeInitialized.await();
            } catch (InterruptedException ignore) {
                // Ignore - it will be ready very soon.
            }
        }
        log.info("wheel {} started finish", id);
    }

    /**
     * 停止时间轮，并返回未完成的任务列表
     *
     * @return
     */
    public List<MyWheelWorker> stop() {
        if (workerState != WORKER_STATE_STARTED) {
            log.info("wheel not started, no need to stop");
            return Collections.emptyList();
        }

        executorService.shutdownNow();
        try {
            while (!executorService.awaitTermination(WAITING_SHUTDOWN_MILLIS, TimeUnit.MILLISECONDS)){
            }
        } catch (InterruptedException e) {
            log.error("executorService stopped" + e.getMessage(), e);
        }

        //调整状态，worker会因为条件不满足停止
        workerState = WORKER_STATE_SHUTDOWN;

        workerThread.shutdownNow();
        try {
            while (!workerThread.awaitTermination(WAITING_SHUTDOWN_MILLIS, TimeUnit.MILLISECONDS)) {
            }
        } catch (InterruptedException e) {
            log.info("workerThread stopped" + e.getMessage(), e);
        }

        Thread.currentThread().interrupt();

        log.info("wheel {} stopped", id);

        return getTaskList();
    }

    /**
     * 新增一个工作
     *
     * @param myWheelWorker
     */
    public void addWheelWorker(MyWheelWorker myWheelWorker) {
        long now = System.currentTimeMillis();
        long diff = myWheelWorker.getTriggerTime() - now;
        if (diff < 0) {
            log.error("worker already expired, can't add!");
            return;
        }
        if (diff < tickDuration) {
            //如果在该轮当前格子的时间范围内，则放到当前轮的下一个格子
            myWheelWorker.setRoundCount(0);
            addTaskWheel(tick + 1, myWheelWorker);
        } else {
            now = System.currentTimeMillis();
            diff = myWheelWorker.getTriggerTime() - now;
            //差的总格子数
            long tickTotal = 1 + diff / tickDuration;
            //默认在当前轮
            long round = 0;
            long thisTick = 0;
            if (tickTotal > ticksPerWheel - tick) {
                //如果总格子数 > 当前轮剩余格子数，则需要计算新轮数
                //先去除当前轮数剩余的格子数
                tickTotal = tickTotal + tick - ticksPerWheel;
                //重新计算轮数
                round = 1 + tickTotal / ticksPerWheel;
                //重新计算偏移量
                thisTick = 1 + tickTotal % ticksPerWheel;
            } else {
                //如果总格子数 < 当前轮剩余格子数，直接放入当前轮数偏移总格子数的位置
                thisTick = tick + tickTotal + 1;
            }

            if (thisTick > Integer.MAX_VALUE) {
                log.error("triggerTime too big, can't add!");
                return;
            }
            myWheelWorker.setRoundCount(round);
            addTaskWheel(Integer.parseInt(String.valueOf(thisTick)), myWheelWorker);
        }
    }

    /**
     * 移除一个工作
     *
     * @param myWheelWorker
     */
    public void removeWheelWorker(MyWheelWorker myWheelWorker) {
        long now = System.currentTimeMillis();
        long diff = myWheelWorker.getTriggerTime() - now;
        if (diff < 0) {
            log.error("worker already expired, can't remove!");
            return;
        }
        removeTaskWheel(myWheelWorker);
    }

    private void removeTaskWheel(MyWheelWorker myWheelWorker) {
        synchronized (taskWheel) {
            Iterator<Map.Entry<Integer, PriorityQueue<MyWheelWorker>>> it = taskWheel.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, PriorityQueue<MyWheelWorker>> entry = it.next();
                PriorityQueue<MyWheelWorker> queue = entry.getValue();
                if (queue == null || queue.isEmpty()) {
                    continue;
                }
                for (MyWheelWorker workerInQueue : queue) {
                    if (workerInQueue.getId().equals(myWheelWorker.getId())) {
                        queue.remove(workerInQueue);
                        return;
                    }
                }
            }
        }
    }

    private void addTaskWheel(int tick, MyWheelWorker myWheelWorker) {
        synchronized (taskWheel) {
            PriorityQueue<MyWheelWorker> thisTickQueue = taskWheel.computeIfAbsent(tick, list -> new PriorityQueue<>());
            thisTickQueue.add(myWheelWorker);
            taskWheel.put(tick, thisTickQueue);
        }
    }

    private List<MyWheelWorker> getTaskList() {
        List<MyWheelWorker> tasks = new ArrayList<>();
        synchronized (taskWheel) {
            for (PriorityQueue queue : taskWheel.values()) {
                if (queue == null || queue.isEmpty()) {
                    continue;
                }
                try {
                    tasks.addAll(queue);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return tasks;
    }

    /**
     * 转化ticksPerWheel为2的N次方
     *
     * @param ticksPerWheel
     * @return
     */
    private static int normalizeTicksPerWheel(int ticksPerWheel) {
        int normalizedTicksPerWheel = 1;
        while (normalizedTicksPerWheel < ticksPerWheel) {
            normalizedTicksPerWheel <<= 1;
        }
        return normalizedTicksPerWheel;
    }

    /**
     * 时间轮主线程要干的工作
     */
    private final class Worker implements Runnable {

        @Override
        public void run() {
            //初始化格子
            tick = 0;

            // Initialize the startTime.
            startTime = System.currentTimeMillis();
            if (startTime == 0) {
                // We use 0 as an indicator for the uninitialized value here, so make sure it's not 0 when initialized.
                startTime = 1;
            }

            // Notify the other threads waiting for the initialization at start().
            startTimeInitialized.countDown();

            //如果时间轮在工作状态，while循环
            do {
                log.info("tick:" + tick);
                final long deadline = waitForNextTick();
                if (deadline > 0) {
                    //取工作队列
                    synchronized (taskWheel) {
                        PriorityQueue<MyWheelWorker> thisTickQueue = taskWheel.computeIfAbsent(tick, list -> new PriorityQueue<>());
                        if (!thisTickQueue.isEmpty()) {
                            //缓存不是这轮的任务
                            PriorityQueue<MyWheelWorker> notThisRoundQueue = new PriorityQueue<>(thisTickQueue.size());
                            thisTickQueue.forEach(myWheelWorker -> {
                                if (myWheelWorker == null) {
                                    return;
                                }
                                //round数不为0，说明还没到触发轮，缓存到下一轮
                                if (myWheelWorker.getRoundCount() > 0) {
                                    //轮数-1
                                    myWheelWorker.setRoundCount(myWheelWorker.getRoundCount() - 1);
                                    notThisRoundQueue.add(myWheelWorker);
                                    return;
                                }

                                try {
                                    //把task分配给线程池，准备触发
                                    executorService.submit(myWheelWorker);
                                } catch (Exception e) {
                                    log.error(e.getMessage(), e);
                                }
                            });
                            //把没执行的非该轮任务，再放回去等待执行
                            taskWheel.put(tick, notThisRoundQueue);
                        }
                    }
                    //步进一格
                    tick++;
                    if (tick >= ticksPerWheel) {
                        //tick 取值范围 [0,ticksPerWheel-1]
                        tick = 0;
                    }
                }
            } while (workerState == WORKER_STATE_STARTED);
        }

        /**
         * 等到下一个触发时间
         * */
        private long waitForNextTick() {
            long sleepTimeMs = tickDuration;
            // Check if we run on windows, as if thats the case we will need
            // to round the sleepTime as workaround for a bug that only affect
            // the JVM if it runs on windows.
            //
            // See https://github.com/netty/netty/issues/356
            //如果是windows平台，先除以10再乘以10，是因为windows平台下最小调度单位是10ms，如果不处理成10ms的倍数，可能导致sleep更不准了
            if (IS_WINDOWS) {
                sleepTimeMs = sleepTimeMs / 10 * 10;
            }
            try {
                Thread.sleep(sleepTimeMs);
            } catch (InterruptedException e) {
                //最后，如果线程被打断了，并且是shutdown状态，会直接返回负数，并在随后的while判断中挑出循环
                if (workerState == WORKER_STATE_SHUTDOWN) {
                    return Long.MIN_VALUE;
                }
            }
            return sleepTimeMs;
        }
    }
}
