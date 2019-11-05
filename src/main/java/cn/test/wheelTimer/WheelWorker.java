package cn.test.wheelTimer;

/**
 * @author Created by chay on 2019/11/2.
 */
public class WheelWorker implements Runnable, Comparable<WheelWorker> {
    /**
     * 任务触发时间
     */
    private long trigTime;
    /**
     * 圈数计数：
     * 初始化圈数=根据触发时间%时间轮一圈的时间
     * 当时间轮转一圈(即扫描过该任务)，圈数-1
     */
    private long roundCount;
    private Runnable runnable;

    public WheelWorker(Long trigTime, Runnable runnable) {
        this.trigTime = trigTime;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        //task to do
        runnable.run();
    }

    @Override
    public int compareTo(WheelWorker o) {
        if (this.trigTime < o.trigTime) {
            return -1;
        } else if (this.trigTime > o.trigTime) {
            return 1;
        } else {
            return 0;
        }
    }
}
