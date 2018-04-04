package test.automic;

/**
 * Created by xiaoni on 2018/4/4.
 */
public class JoinExample {
    public static void main(String[] args) throws InterruptedException
    {
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("First task started");
                System.out.println("Sleeping for 2 seconds");
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("First task completed");
            }
        });
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("Second task completed");
            }
        });
        t.start(); // Line 15
        //如果不使用join，t1不会等待t结束就执行了。
        /**
         * 线程实例的方法join()方法可以使得一个线程在另一个线程结束后再执行。
         * 如果join()方法在一个线程实例上调用，当前运行着的线程将阻塞直到这个线程实例完成了执行。
         */
        t.join(); // Line 16
        t1.start();
    }
}
