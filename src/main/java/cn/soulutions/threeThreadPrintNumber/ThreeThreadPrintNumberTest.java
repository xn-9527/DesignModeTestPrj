package cn.soulutions.threeThreadPrintNumber;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: chay
 * @date: 2021/7/1 22:05 
 * @description:
 */
public class ThreeThreadPrintNumberTest {

    /**
     题目 2
     编写一个程序，开启3个线程，
     这3个线程的ID分别为A、B、C，3个线程交替打印1-100的整数，要求输出结果有序,
     样例Sample:
     Thread1: 1
     Thread2: 2
     Thread3: 3
     Thread1: 4
     Thread2: 5
     Thread3: 6
     ....
     Thread3: 99
     Thread1: 100
     */
    public static class MyPrint {
        final ReentrantLock lock = new ReentrantLock();
        final Condition a = lock.newCondition();
        final Condition b = lock.newCondition();
        final Condition c = lock.newCondition();
        int i = 0;
        int state = 1;
        public void printA() {
            try {
                lock.lock();
                while (i <= 100) {
                    while (state == 1) {
                        System.out.println(Thread.currentThread().getName() + ":" + i++);
                        state = 2;
                        b.signal();
                    }
                    try {
                        a.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }

        public void printB() {
            try {
                lock.lock();
                while (i <= 100) {
                    while (state == 2) {
                        System.out.println(Thread.currentThread().getName() + ":" + i++);
                        state = 3;
                        c.signal();
                    }
                    try {
                        b.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }

        public void printC() {
            try {
                lock.lock();
                while (i <= 100) {
                    while (state == 3) {
                        System.out.println(Thread.currentThread().getName() + ":" + i++);
                        state = 1;
                        a.signal();
                    }
                    try {
                        c.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        MyPrint myPrint = new MyPrint();

        Thread aThread = new Thread(() -> {
            myPrint.printA();
        });
        Thread bThread = new Thread(() -> {
            myPrint.printB();
        });
        Thread cThread = new Thread(() -> {
            myPrint.printC();
        });
        aThread.start();
        bThread.start();
        cThread.start();
    }
}
