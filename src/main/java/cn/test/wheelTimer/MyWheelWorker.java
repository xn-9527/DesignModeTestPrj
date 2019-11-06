package cn.test.wheelTimer;

import com.alibaba.fastjson.JSON;
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
     * 时间轮工人id，整个系统全局唯一
     */
    private static final AtomicInteger AUTO_ID = new AtomicInteger();
    /**
     * 对象的id
     */
    private int id;
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

    public MyWheelWorker(long triggerTime, Runnable runnable) {
        AUTO_ID.incrementAndGet();
        this.id = AUTO_ID.get();
        this.triggerTime = triggerTime;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        //task to do
        long now = System.currentTimeMillis();
        long timeUntilTrigger = triggerTime - now;
        //try catch 一定要在while循环外，否则线程无法被打断停止
        try {
            //以2ms为最低触发时限，阻塞线程直到触发时间，再触发任务
            while (timeUntilTrigger > 2) {
                now = System.currentTimeMillis();
                timeUntilTrigger = triggerTime - now;

                if (timeUntilTrigger > 6) {
                    Thread.sleep(timeUntilTrigger - 3);
                }
            }
            log.debug("trigger time : {}", System.currentTimeMillis());
            runnable.run();
        } catch (InterruptedException e) {
            log.error("worker {} interrupted, work {} will not be done", id, JSON.toJSONString(runnable));
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
