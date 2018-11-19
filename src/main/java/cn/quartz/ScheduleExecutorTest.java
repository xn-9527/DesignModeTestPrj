package cn.quartz;

import org.apache.log4j.Logger;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by xiaoni on 2018/10/17.
 */
public class ScheduleExecutorTest {

    private final ScheduledExecutorService scheduledExecutor = new ScheduledThreadPoolExecutor(12);

    private static Logger logger = Logger.getLogger(ScheduleExecutorTest.class);

    public ScheduleExecutorTest() {
        this.testSchedule();
        this.testSchedule2();
    }

    private long lastWith = 0;
    private long lastAt = 0;

    public void testSchedule() {
        //测试固定的每1秒延迟
        scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                System.out.println("scheduleWithFixedDelay 1 seconds" + (now - lastWith));
                logger.info("scheduleWithFixedDelay 1 seconds" + (now - lastWith));
                lastWith = now;
                try {
                    for (int i = 0;i < 3;i++) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void testSchedule2() {
        //测试上次结束后的每1秒延迟
        scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                System.out.println("scheduleAtFixedRate 1 seconds" + (now - lastAt));
                lastAt = now;
                try {
                    for (int i = 0;i < 3;i++) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
