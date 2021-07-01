package cn.soulutions.threeThreadPrintNumber;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: nichengyun
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
    public static class MyPrint extends Thread {
        private Integer i;
        private Condition wait;
        private Condition signal;

        public MyPrint(Integer i, Condition wait, Condition signal) {
            this.i = i;
            this.wait = wait;
            this.signal = signal;
        }

        @Override
        public void run() {
            if (i <= 100) {
                System.out.println(i++);
                signal.signal();
                try {
                    wait.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                this.interrupt();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();
        Integer i = 0;

        MyPrint aThread = new MyPrint(i, a, b);
        MyPrint bThread = new MyPrint(i, b, c);
        MyPrint cThread = new MyPrint(i, c, a);
        aThread.start();
        bThread.start();
        cThread.start();
    }
}
