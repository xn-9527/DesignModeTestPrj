package cn.soulutions.queueTest;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Chay
 * @date 2021/5/25 19:52
 */
public class QueueProducerConsumer {

    public static class Producer {
        private ArrayBlockingQueue q;
        public Producer(ArrayBlockingQueue q) {
            this.q = q;
        }

        public void produce() {
            if (q == null) {
                return;
            }
            for (int i = 0; i < 10; i ++) {
                try {
                    String msg = Thread.currentThread().getName() + "msg:" + i;
                    q.put(msg);
                    System.out.println("producer:" + msg);
                    Thread.sleep(RandomUtils.nextLong(100L, 5000L));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer {
        private ArrayBlockingQueue q;
        public Consumer(ArrayBlockingQueue q) {
            this.q = q;
        }
        public void consumer() {
            while (true) {
                try {
                    Object o = q.take();
                    System.out.println(Thread.currentThread().getName() + "consumer:" + o);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Producer p = new Producer(queue);
        Consumer c = new Consumer(queue);
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    p.produce();
                }
            }).start();
        }
        for (int j = 0; j < 2; j++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    c.consumer();
                }
            }).start();
        }
    }

}
