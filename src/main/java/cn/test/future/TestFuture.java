package cn.test.future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Created by xiaoni on 2019/7/12.
 */
@Slf4j
public class TestFuture {
    ReentrantLock reentrantLock = new ReentrantLock();
    ReentrantLock mainObjectLock = new ReentrantLock();
    ExecutorService executorService = new ThreadPoolExecutor(5, 30, 5000L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024),
            new ThreadFactoryBuilder().setNameFormat("sendHttpMessage-Pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());

    public void test(String threadName) throws Exception {
        //单台机器在50秒内，向云端请求最多25次(间隔2s/次)，所以线程池容量200理论最大可以支持8台同时网络不好的最极端情况，超过该能力后可能后台仍然卡死
        Future<String> future = executorService.submit(new CallableAjaxResult(threadName));
        int i = 0;
        while (!future.isDone()) {
            try {
                log.info(threadName + "future is done count:" + i++);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        try {
            log.info(threadName + "test response: {}", future.get());
        } catch (InterruptedException e) {
            log.error(threadName + e.getMessage(), e);
        } catch (ExecutionException e) {
            log.error(threadName + e.getMessage(), e);
        }
    }

    /**
     * 模拟主线程掉用数据库锁
     */
    public void test2MainLock(String threadName) {
        try {
            if (mainObjectLock.tryLock()) {
                log.info(threadName + "拿到了mainObject锁");
            } else {
                log.info(threadName + "没拿到mainObject锁");
            }
        } catch (Exception e) {
            log.error(threadName + e.getMessage(), e);
        } finally {
            try {
                if (mainObjectLock != null && mainObjectLock.isLocked()) {
                    log.info(threadName + "最终释放mainObject锁");
                    mainObjectLock.unlock();
                }
            } catch (IllegalMonitorStateException e) {
                log.info(threadName + "当前线程没有占用mainObject锁" + e.getMessage(), e);
            }
        }
    }

    class CallableAjaxResult implements Callable<String> {
        private String threadName;

        CallableAjaxResult(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public String call() throws Exception {
            log.info("开始写入test,threadName=" + threadName);
            try {
                String uuid = UUID.randomUUID().toString();
                try {
                    test2MainLock(threadName);
                    if (!reentrantLock.tryLock()) {
                        log.debug(threadName + "未获取到锁，下次再试");
                        return threadName + "未获取到锁，下次再试";
                    }
                    log.info(threadName + "开始等待消息{}回执:{}", uuid, System.currentTimeMillis());
                    for (int i = 0; i < 10; i++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            log.error(e.getMessage(), e);
                        }
                        if (i == 8) {
                            throw new Exception(threadName + "test error");
                        }
                    }
                    log.info(threadName + "收到消息{}机器人回执:{}", uuid, System.currentTimeMillis());
                    return threadName + "success 1";
                } catch (Exception e) {
                    log.error(threadName + e.getMessage(), e);
                    return threadName + e.getMessage();
                } finally {
                    try {
                        if (reentrantLock != null && reentrantLock.isLocked()) {
                            log.info(threadName + "最终释放锁");
                            reentrantLock.unlock();
                        }
                    } catch (IllegalMonitorStateException e) {
                        log.info(threadName + "当前线程没有占用锁" + e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                log.error(threadName + e.getMessage(), e);
                return threadName + e.getMessage();
            }
        }
    }

    public static void main(String[] args) {
        TestFuture testFuture = new TestFuture();
        try {
            for (int i = 0; i < 10; i++) {
                final int j = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testFuture.test("thread-" + j + "|||||");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            for (int i = 10; i < 20; i++) {
                final int j = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testFuture.test("thread-" + j + "|||||");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
