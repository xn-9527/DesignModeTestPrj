package cn.soulutions.threadTestPrintXYZ;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写代码，使用3个线程，1个线程打印X，一个线程打印Y，一个线程打印Z，同时执行连续打印10次"XYZ"
 * Created by xiaoni on 2020/3/26.
 */
public class ThreadTest3 {

    public static void main(String[] args) {
        ThreadTest3 threadTest2Service = new ThreadTest3();
        Thread a = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                threadTest2Service.printX();
            }
        });
        Thread b = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                threadTest2Service.printY();
            }
        });
        Thread c = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                threadTest2Service.printZ();
            }
        });
        a.start();
        b.start();
        c.start();
    }

    private int flag = 1;
    private Lock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();

    /**
     * 当使用LOCK时可以不使用while因为condition可以唤醒指定的线程。
     * 同时注意必须先调用 conditionA.signal(); 再调用 lock.unlock();
     * 否则会抛 java.lang.IllegalMonitorStateException 异常。
     * 因为在调用unlock之后，当前线程已不是此监视器对象condition的持有者。
     * 也就是说要在此线程持有锁定对象时，才能使用此锁定对象。
     *
     * TIPS:注意这里的方法不能加synchronized，因为里面已经有锁了，跟上面的不一样
     * 导致结果只打印一个X然后就一直阻塞在线程a里面
     * 因为for循环的第二次，state=2，然后线程a又拿到了synchronized的锁，然后就一直conditionA.await()
     */
    public void printX() {
        lock.lock();
        try {
            if (flag != 1) {
                try {
                    conditionA.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("X");
            flag = 2;
            conditionB.signal();
        } finally {
            lock.unlock();
        }
    }

    public void printY() {
        lock.lock();
        try {
            if (flag != 2) {
                try {
                    conditionB.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Y");
            flag = 3;
            conditionC.signal();
        } finally {
            lock.unlock();
        }
    }

    public void printZ() {
        lock.lock();
        try {
            if (flag != 3) {
                try {
                    conditionC.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Z");
            flag = 1;
            conditionA.signal();
        } finally {
            lock.unlock();
        }
    }
}
