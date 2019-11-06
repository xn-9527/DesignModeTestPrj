package cn.test.wheelTimer;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoni on 2019/11/5.
 */
@Slf4j
public class WheelTimerTest {

    public static void main(String[] args) {
        log.info("" + -1/3);
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 5000L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024),
                new ThreadFactoryBuilder().setNameFormat("myWheelTimer-Pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        MyWheelTimer myWheelTimer = new MyWheelTimer(executorService, 600L, TimeUnit.MILLISECONDS, 10);
//        MyWheelTimer myWheelTimer2 = new MyWheelTimer(executorService, 600L, TimeUnit.MILLISECONDS, 10);

        long now = System.currentTimeMillis();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                log.info("I'm run 1");
            }
        };
        WheelWorker wheelWorker1 = new WheelWorker(now + 10000L, runnable1);

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                log.info("I'm run 2");
            }
        };
        WheelWorker wheelWorker2 = new WheelWorker(now + 15000L, runnable2);

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                log.info("I'm run 3");
            }
        };
        WheelWorker wheelWorker3 = new WheelWorker(now + 200000000L, runnable3);

        myWheelTimer.addWheelWorker(wheelWorker1);
        myWheelTimer.addWheelWorker(wheelWorker2);
        myWheelTimer.addWheelWorker(wheelWorker3);
        myWheelTimer.start();
//        myWheelTimer2.start();

        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("try stop myWheelTimer");
        log.info(JSON.toJSONString(myWheelTimer.stop()));
    }
}
