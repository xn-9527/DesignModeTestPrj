package cn.soulutions.threadTest;

/**
 * 编写代码，使用3个线程，1个线程打印X，一个线程打印Y，一个线程打印Z，同时执行连续打印10次"XYZ"
 * Created by xiaoni on 2020/3/26.
 */
public class ThreadTest {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread a = new Thread(() -> {
                System.out.println("X");
            });
            Thread b = new Thread(() -> {
                try {
                    a.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Y");
            });
            Thread c = new Thread(() -> {
                try {
                    b.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Z");
            });
            a.start();
            b.start();
            c.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }
}
