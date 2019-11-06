package cn.test.wheelTimer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by chay on 2019/11/2.
 */
@Data
@Slf4j
public class MyWheelWorker implements Runnable, Comparable<MyWheelWorker> {
    /**
     * 最小的触发毫秒数
     */
    private final static long MIN_TRIGGER_MILLIS = 2L;
    /**
     * 睡眠预留处理时间
     */
    private final static long SLEEP_PRE_TRIGGER_MILLIS = 3L;

    /**
     * 时间轮工人id生成器，整个系统全局唯一
     */
    private static final AtomicInteger AUTO_ID = new AtomicInteger();
    /**
     * 对象的id，可外部指定，如果不指定，则由生成器生成。
     */
    private String id;
    /**
     * 任务触发时间
     */
    private long triggerTime;
    /**
     * 圈数计数：
     * 初始化圈数=根据触发时间%时间轮一圈的时间
     * 当时间轮转一圈(即扫描过该任务)，圈数-1
     */
    private long roundCount;
    private Runnable runnable;
    /**
     * 当任务中止时，需要显示的消息
     */
    private String interruptMessage;

    public MyWheelWorker(long triggerTime, Runnable runnable, String workerUuid, String interruptMessage) {
        if (triggerTime <= 0) {
            throw new IllegalArgumentException("triggerTime must bigger than 0:" + triggerTime);
        }
        if (runnable == null) {
            throw new NullPointerException("runnable is null.");
        }

        if (workerUuid == null || workerUuid.isEmpty()) {
            AUTO_ID.incrementAndGet();
            this.id = String.valueOf(AUTO_ID.get());
        } else {
            this.id = workerUuid;
        }
        this.triggerTime = triggerTime;
        this.runnable = runnable;
        this.interruptMessage = interruptMessage;
    }

    public MyWheelWorker(long triggerTime, Runnable runnable, String interruptMessage) {
        this(triggerTime, runnable, null, interruptMessage);
    }

    public MyWheelWorker(long triggerTime, Runnable runnable) {
        this(triggerTime, runnable, null, null);
    }

    @Override
    public void run() {
        //task to do
        long now = System.currentTimeMillis();
        long timeUntilTrigger = triggerTime - now;
        //try catch 一定要在while循环外，否则线程无法被打断停止
        try {
            //以2ms为最低触发时限，阻塞线程直到触发时间，再触发任务
            while (timeUntilTrigger > MIN_TRIGGER_MILLIS) {
                now = System.currentTimeMillis();
                timeUntilTrigger = triggerTime - now;

                if (timeUntilTrigger > 2 * SLEEP_PRE_TRIGGER_MILLIS) {
                    Thread.sleep(timeUntilTrigger - SLEEP_PRE_TRIGGER_MILLIS);
                }
            }
            log.debug("trigger time : {}", System.currentTimeMillis());
            runnable.run();
        } catch (InterruptedException e) {
            log.error("worker {} interrupted, work will not be done. {}", id, interruptMessage);
            log.error("worker " + id + " interrupted " + e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int compareTo(MyWheelWorker o) {
        if (this.triggerTime < o.triggerTime) {
            return -1;
        } else if (this.triggerTime > o.triggerTime) {
            return 1;
        } else {
            return 0;
        }
    }
}
