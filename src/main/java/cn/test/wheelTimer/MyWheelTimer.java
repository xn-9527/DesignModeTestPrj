package cn.test.wheelTimer;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by chay on 2019/11/2.
 */
@Slf4j
public class MyWheelTimer {
    private static final boolean IS_WINDOWS;
    static {
        String os = System.getProperty("os.name");;
        // windows
        IS_WINDOWS =  os.toLowerCase().contains("win");
    }

    /**
     * 时间轮id，整个系统全局唯一
     */
    private static final AtomicInteger id = new AtomicInteger();
    /**
     * 线程池
     */
    private ExecutorService executorService;
    /**
     * 任务缓存:
     * key : 格子编号
     * value： 该格子内的任务，自动按照触发时间升序排序
     */
    private final ConcurrentHashMap<Integer, PriorityQueue<? extends WheelWorker>> taskWheel;
    /**
     * 一圈的格数，2的次方
     */
    private Integer ticksPerWheel;
    /**
     * 一格时间
     */
    private long tickDuration;
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
    @SuppressWarnings({ "unused", "FieldMayBeFinal", "RedundantFieldInitialization" })
    private volatile int workerState = WORKER_STATE_INIT; // 0 - init, 1 - started, 2 - shut down

    public MyWheelTimer(ExecutorService executorService,
                        long tickDuration, TimeUnit unit, int ticksPerWheel) {
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

        this.executorService = executorService;

        // Normalize ticksPerWheel to power of two and initialize the wheel.
        this.ticksPerWheel = ticksPerWheel;
        taskWheel = createWheel(ticksPerWheel);

        // Convert tickDuration to nanos.
        this.unit = unit;
        this.tickDuration = unit.toNanos(tickDuration);

        // Prevent overflow.
        if (this.tickDuration >= Long.MAX_VALUE / taskWheel.size()) {
            throw new IllegalArgumentException(String.format(
                    "tickDuration: %d (expected: 0 < tickDuration in nanos < %d",
                    tickDuration, Long.MAX_VALUE / taskWheel.size()));
        }
    }

    /**
     * 根据轮子格子数，近似到2的N次方，然后初始化每个格子的任务队列
     *
     * @param ticksPerWheel
     * @return
     */
    @SuppressWarnings("unchecked")
    private static ConcurrentHashMap<Integer, PriorityQueue<? extends WheelWorker>> createWheel(int ticksPerWheel) {
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException(
                    "ticksPerWheel must be greater than 0: " + ticksPerWheel);
        }
        if (ticksPerWheel > 1073741824) {
            throw new IllegalArgumentException(
                    "ticksPerWheel may not be greater than 2^30: " + ticksPerWheel);
        }

        ticksPerWheel = normalizeTicksPerWheel(ticksPerWheel);
        ConcurrentHashMap<Integer, PriorityQueue<? extends WheelWorker>> wheel = new ConcurrentHashMap<Integer, PriorityQueue<? extends WheelWorker>>(ticksPerWheel);
        for (int i = 0; i < wheel.size(); i ++) {
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
    }

    /**
     * 停止时间轮，并返回未完成的任务列表
     *
     * @return
     */
    public List<? extends WheelWorker> stop() {
        if (workerState != WORKER_STATE_STARTED) {
            log.info("wheel not started, no need to stop");
            return Collections.emptyList();
        }
        boolean interrupted = false;
        while (!workerThread.isTerminated()) {
            workerThread.shutdown();
            try {
                workerThread.awaitTermination(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }

        if (interrupted) {
            Thread.currentThread().interrupt();
        }

        log.info("wheel {} stopped", id);
        workerState = WORKER_STATE_SHUTDOWN;
        return getTaskList();
    }

    private List<? extends WheelWorker> getTaskList() {
        List<? extends WheelWorker> tasks = new ArrayList<>();
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
        /**
         * 当前转到的格子数
         */
        private int tick;
        @Override
        public void run() {
            //初始化格子
            tick = 0;

            // Initialize the startTime.
            startTime = System.nanoTime();
            if (startTime == 0) {
                // We use 0 as an indicator for the uninitialized value here, so make sure it's not 0 when initialized.
                startTime = 1;
            }

            // Notify the other threads waiting for the initialization at start().
            startTimeInitialized.countDown();

            //如果时间轮在工作状态，while循环
            do {
                final long deadline = waitForNextTick();
                if (deadline > 0) {
                    //取工作队列
                    PriorityQueue<? extends WheelWorker> thisTickQueue = taskWheel.computeIfAbsent(tick, list -> new PriorityQueue<>());

                    //步进一格
                    tick++;
                }
            } while (workerState == WORKER_STATE_STARTED);
        }

        /**
         * calculate goal nanoTime from startTime and current tick number,
         * then wait until that goal has been reached.
         * @return Long.MIN_VALUE if received a shutdown request,
         * current time otherwise (with Long.MIN_VALUE changed by +1)
         */
        private long waitForNextTick() {
            long deadline = tickDuration * (tick + 1);

            for (;;) {
                final long currentTime = System.nanoTime() - startTime;
                long sleepTimeMs = (deadline - currentTime + 999999) / 1000000;

                if (sleepTimeMs <= 0) {
                    if (currentTime == Long.MIN_VALUE) {
                        return -Long.MAX_VALUE;
                    } else {
                        return currentTime;
                    }
                }

                // Check if we run on windows, as if thats the case we will need
                // to round the sleepTime as workaround for a bug that only affect
                // the JVM if it runs on windows.
                //
                // See https://github.com/netty/netty/issues/356
                if (IS_WINDOWS) {
                    sleepTimeMs = sleepTimeMs / 10 * 10;
                }

                try {
                    Thread.sleep(sleepTimeMs);
                } catch (InterruptedException e) {
                    if (workerState == WORKER_STATE_SHUTDOWN) {
                        return Long.MIN_VALUE;
                    }
                }
            }
        }
    }
}
