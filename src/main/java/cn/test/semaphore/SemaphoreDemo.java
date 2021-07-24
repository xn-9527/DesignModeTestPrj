package cn.test.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @Author: chay
 * @Date: 2021/4/11 12:33
 * 十个线程抢5个令牌
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //令牌数
        Semaphore semaphore = new Semaphore(5);
        for(int i = 0;i<10;i++) {
            new Car(semaphore, i).start();
        }
    }

    static class Car extends Thread {
        Semaphore semaphore;
        int num;
        public Car(Semaphore semaphore, int num) {
            this.semaphore = semaphore;
            this.num = num;
        }

        @Override
        public void run() {
            try {
                //获得令牌：没拿到会阻塞，拿到了就可以往下执行
                semaphore.acquire();
                System.out.println(System.currentTimeMillis() + ":第" + num + "线程占用一个令牌");
                Thread.sleep(3000);
                semaphore.release();
                System.out.println(System.currentTimeMillis() + ":第" + num + "线程释放一个令牌");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
