package cn.test.testLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-10-23 下午01:53:10 类说明
 * lockInterruptibly()和上面的第一种情况是一样的， 线程在请求lock并被阻塞时，如果被interrupt，
 * 则“此线程会被唤醒并被要求处理InterruptedException”。
 * 并且如果线程已经被interrupt，再使用lockInterruptibly的时候，此线程也会被要求处理interruptedException
 */
public class TestLockInterruptibly
{

    // @Test
    public void test3() throws Exception
    {
        final Lock lock = new ReentrantLock();
        lock.lock();

        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
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

        Thread.sleep(1000000);
    }

    public static void main(String[] args) throws Exception
    {
        new TestLockInterruptibly().test3();
    }
}