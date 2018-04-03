package test.threadpool;

import java.util.concurrent.*;

/**
 * Created by xiaoni on 2018/4/3.
 */
public class WorkerPool {
    public static void main(String args[]) throws InterruptedException{
        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        /**
         * 4.任务缓存队列及排队策略
         workQueue的类型为BlockingQueue，通常可以取下面三种类型：
         1）ArrayBlockingQueue：基于数组的先进先出队列，此队列创建时必须指定大小；
         2）LinkedBlockingQueue：基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE；
         3）synchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。
         5.任务拒绝策略
         前面已经讲过， 当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略，通常有以下四种策略：
         ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
         ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
         */
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
        //start the monitoring thread
        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        //submit work to the thread pool
        for(int i=0; i<10; i++){
            executorPool.execute(new WorkerThread("cmd"+i));
        }

        Thread.sleep(30000);
        //shut down the pool
        executorPool.shutdown();
        //shut down the monitor thread
        Thread.sleep(5000);
        monitor.shutdown();

    }
}
