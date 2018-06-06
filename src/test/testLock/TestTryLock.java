package test.testLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiaoni on 2018/6/6.
 * tryLock()，马上返回，拿到lock就返回true，不然返回false。 比较潇洒的做法。
 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false。比较聪明的做法。
 */
public class TestTryLock {

    // @Test
    public void test() throws Exception
    {
        final Lock lock = new ReentrantLock();
        System.out.println(lock.tryLock() + " " + System.currentTimeMillis());


        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                try {
                    /**
                     * 如果该锁没有被另一个线程保持，并且立即返回 true 值，则将锁的保持计数设置为 1。
                     即使已将此锁设置为使用公平排序策略，但是调用 tryLock() 仍将 立即获取锁（如果有可用的），
                     而不管其他线程当前是否正在等待该锁。在某些情况下，此“闯入”行为可能很有用，即使它会打破公
                     平性也如此。如果希望遵守此锁的公平设置，则使用 tryLock(0, TimeUnit.SECONDS)
                     */
//                    System.out.println(lock.tryLock());
                    System.out.println(lock.tryLock(0, TimeUnit.SECONDS) + " " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " finish");
            }
        },"child thread -1");

        t1.start();
        Thread.sleep(1000);

//        t1.interrupt();

        Thread.sleep(10000);
    }

    public static void main(String[] args) throws Exception
    {
        new TestTryLock().test();
    }
}
