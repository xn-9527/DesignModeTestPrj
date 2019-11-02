package cn.test.wheelTimer;

import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by chay on 2019/11/2.
 */
public class MyWheelTimer {
    /**
     * 线程池
     */
    private ExecutorService executorService;
    /**
     * 任务缓存:
     * key : 轮数
     */
    private ConcurrentHashMap<Integer, PriorityQueue<? extends WheelWorker>> taskMap;
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
     * 圈数计数
     */
    private long roundCount;

    public void start() {

    }

    public void stop() {

    }
}
