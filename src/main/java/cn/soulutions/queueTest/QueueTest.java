package cn.soulutions.queueTest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 通过阻塞队列实现写一个生产者-消费者队列.
 * Created by xiaoni on 2020/3/26.
 */
public class QueueTest {
    public static void main(String[] args) {
        BlockingQueue queue = new LinkedBlockingQueue();

        Thread producerThread = new Thread(new ProducerRunnable(queue, "p1,"));
        Thread producerThread2 = new Thread(new ProducerRunnable(queue, "p2,"));
        Thread consumerThread = new Thread(new ConsumerRunnable(queue, "c1,"));

        producerThread.start();
        producerThread2.start();
        consumerThread.start();
    }

    public static class ProducerRunnable implements Runnable {
        private final BlockingQueue queue;
        private final String name;

        public ProducerRunnable(BlockingQueue blockingQueue, String name) {
            this.queue = blockingQueue;
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                String message = name + i;
                System.out.println("Produced: message" + message);
                queue.offer(message);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ConsumerRunnable implements Runnable {
        private final BlockingQueue queue;
        private final String name;

        public ConsumerRunnable(BlockingQueue queue, String name) {
            this.queue = queue;
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                while (queue.isEmpty()) {
                    Thread.yield();
//                    try {
                    //this.wait();会抛出IllegalMonitorStateException异常，说当前线程不是对象监视器的主人
//                        this.wait();
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                    }
                }
                try {
                    System.out.println(name + "Consumed : message" + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
