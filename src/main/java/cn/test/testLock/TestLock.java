package cn.test.testLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-10-23 下午01:47:03 类说明
 */
public class TestLock {
    // @Test
    public void test() throws Exception
    {
        final Lock lock = new ReentrantLock();
        lock.lock();


        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //即使子线程已经被打断，但是子线程仍然在run，可见lock()方法并不关心线程是否被打断，甚至说主线程已经运行完毕，子线程仍然在block().
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " finish");
            }
        },"child thread -1");

        t1.start();
        Thread.sleep(1000);

        t1.interrupt();

        Thread.sleep(10000);
    }

    public static void main(String[] args) throws Exception
    {
        new TestLock().test();
    }
}