package test.testLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-10-23 下午01:53:10 类说明
 */
public class TestLockInterruptibly2
{

    // @Test
    public void test3() throws Exception
    {
        final Lock lock = new ReentrantLock();
//        lock.lock();

        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //将会在在阻塞之前已经中断，此时再lockInterruptibly()也是会相应中断异常的
                    Thread.sleep(2000);
                    lock.lockInterruptibly();
                }
                catch(InterruptedException e)
                {
                    System.out.println(Thread.currentThread().getName() + " interrupted.");
                }
            }
        }, "child thread -1");


        t1.start();
        Thread.sleep(1000);

        t1.interrupt();

        Thread.sleep(10000);
    }

    public static void main(String[] args) throws Exception
    {
        new TestLockInterruptibly2().test3();
    }
}