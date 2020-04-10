package cn.soulutions.threadTestPrintXYZ;

/**
 * 编写代码，使用3个线程，1个线程打印X，一个线程打印Y，一个线程打印Z，同时执行连续打印10次"XYZ"
 * Created by xiaoni on 2020/3/26.
 */
public class ThreadTest2 {

    public static void main(String[] args) {
        ThreadTest2 threadTest2Service = new ThreadTest2();
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

    public synchronized void printX() {
        while (flag != 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("X");
        flag = 2;
        //注意，这里必需notifyall，要不然会死循环。
        this.notifyAll();
    }

    public synchronized void printY() {
        while (flag != 2) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Y");
        flag = 3;
        this.notifyAll();
    }

    public synchronized void printZ() {
        while (flag != 3) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Z");
        flag = 1;
        this.notifyAll();
    }
}
