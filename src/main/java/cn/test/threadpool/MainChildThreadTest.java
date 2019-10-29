package cn.test.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Created by xiaoni on 2019/10/29.
 * 测试主线程会不会被子线程阻塞
 */
@Slf4j
public class MainChildThreadTest {
    /**
     * -corePoolSize：核心池的大小，这个参数跟后面讲述的线程池的实现原理有非常大的关系。在创建了线程池后，默认情况下，线程池中并没有任何线程，而是等待有任务到来才创建线程去执行任务，
     *  除非调用了prestartAllCoreThreads()或者prestartCoreThread()方法，从这2个方法的名字就可以看出，是预创建线程的意思，即在没有任务到来之前就创建corePoolSize个线程或者一个线程。
     *  默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
     * -maximumPoolSize：线程池最大线程数，这个参数也是一个非常重要的参数，它表示在线程池中最多能创建多少个线程；
     -keepAliveTime：表示线程没有任务执行时最多保持多久时间会终止。默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会起作用，直到线程池中的线程数不大于corePoolSize，即当线程池中的线程数大于corePoolSize时，如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。但是如果调用了allowCoreThreadTimeOut(boolean)方法，在线程池中的线程数不大于corePoolSize时，keepAliveTime参数也会起作用，直到线程池中的线程数为0；
     -unit：参数keepAliveTime的时间单位，有7种取值。TimeUnit.DAYS、TimeUnit.HOURS、TimeUnit.MINUTES、TimeUnit.SECONDS、TimeUnit.MILLISECONDS、TimeUnit.MICROSECONDS、TimeUnit.NANOSECONDS
     -workQueue：一个阻塞队列，用来存储等待执行的任务，这个参数的选择也很重要，会对线程池的运行过程产生重大影响，一般来说，
     这里的阻塞队列有以下几种选择：ArrayBlockingQueue、LinkedBlockingQueue、SynchronousQueue。
     ArrayBlockingQueue和PriorityBlockingQueue使用较少，一般使用LinkedBlockingQueue和Synchronous。线程池的排队策略与BlockingQueue有关。
     -threadFactory：线程工厂，主要用来创建线程；
     -handler：表示当拒绝处理任务时的策略，有以下四种取值：
     *
     */
    ExecutorService pool = new ThreadPoolExecutor(2, 200, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1),
            new ThreadFactoryBuilder().setNameFormat("test-MainChildThread").build(),
            new ThreadPoolExecutor.AbortPolicy());

    public void work(String workerName) {
        Future<String> future = pool.submit(new CallableResult(workerName));
        //while循环会阻塞主线程，如果不加while循环，会直接主线程执行完毕
        while (!future.isDone()) {
            try {
                log.info(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        log.info("mainThread method wait worker {} is done", workerName);
    }

    class CallableResult implements Callable<String> {
        private String threadName;

        CallableResult(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public String call() throws Exception {
            log.info(threadName + " start");
            for (int i = 0; i < 20; i++) {
                log.info(threadName + "," + i);
                Thread.sleep(1000L);
            }
            return threadName + " done";
        }
    }

    public static void main(String[] args) {
        MainChildThreadTest mainChildThreadTest = new MainChildThreadTest();
        /**
         * 我们核心线程数是2，所以可以看到下面的3个主线程，每个主线程2个子线程，
         * 共6个worker线程，
         * 由于我们每个主线程会等待自己的worker给回执了才开始下一个work，所以实际会
         * 有2个会直接执行，有4个会等待执行
         */
        for (int i = 0; i < 4; i++) {
            final int j = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mainChildThreadTest.work("worker" + j + "-1");
                    mainChildThreadTest.work("worker" + j + "-2");
                    log.info("mainThread {} done", j);
                }
            }).start();
        }
        /**
         * 结论：单例对象，即使是多主线程调用同一实例的相同子线程工作方法，具体实例方法的实际工作数量 = 主线程数 - 缓存queue大小，
         * 根据线程池理论，当达到corePoolSize,其他任务都会被缓存在queue里面。如果queue满了，才会新建线程去执行任务。
         */
    }
}
