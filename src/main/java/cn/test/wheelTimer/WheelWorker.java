package cn.test.wheelTimer;

/**
 * @author Created by chay on 2019/11/2.
 */
public class WheelWorker implements Runnable, Comparable<WheelWorker> {
    /**
     * 任务触发时间
     */
    private long trigTime;
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
