package cn.soulutions.queueTest;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 通过阻塞队列实现写一个生产者-消费者队列.
 * Created by xiaoni on 2020/3/26.
 */
public class QueueTest {
    public static void main(String[] args) {
        BlockingQueue queue = new LinkedBlockingQueue();

        Thread producerThread = new Thread(new ProducerRunnable(queue));
        Thread consumerThread = new Thread(new ConsumerRunnable(queue));

        producerThread.start();
        consumerThread.start();
    }

    public static class ProducerRunnable implements Runnable {
        private final BlockingQueue queue;

        public ProducerRunnable(BlockingQueue blockingQueue) {
            this.queue = blockingQueue;
        }

        @Override
        public void run() {
            for (int i=0;i<10;i++) {
                System.out.println("Produced: message" + i);
                queue.offer(i);
            }
        }
    }

    public static class ConsumerRunnable implements Runnable {
        private final BlockingQueue queue;

        public ConsumerRunnable(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
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
                    System.out.println("Consumed : message" + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
