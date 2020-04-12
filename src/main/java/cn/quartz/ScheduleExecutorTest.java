package cn.quartz;

import cn.test.service.TestUserService;
import cn.test.testYamlValue.TestYamlValueService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by xiaoni on 2018/10/17.
 */
@Component
public class ScheduleExecutorTest {

    private final ScheduledExecutorService scheduledExecutor = new ScheduledThreadPoolExecutor(12);

    private static Logger logger = Logger.getLogger(ScheduleExecutorTest.class);

    @Autowired
    private TestUserService testUserService;
    @Autowired
    private TestYamlValueService testYamlValueService;

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
                //测试Service的AutoWired，不加@Component注解autoWired是空的，加了就不是空的
                testUserService.sayHello();
                testYamlValueService.testAB();
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
