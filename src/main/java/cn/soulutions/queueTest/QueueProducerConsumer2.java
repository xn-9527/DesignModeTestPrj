package cn.soulutions.queueTest;

import org.apache.commons.lang3.RandomUtils;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Chay
 * @date 2021/5/25 19:52
 */
public class QueueProducerConsumer2 {

    public static class Producer {
        private Queue q;
        int maxsize;

        public Producer(Queue q) {
            this.q = q;
            this.maxsize = 10;
        }

        public void produce() {
            if (q == null) {
                return;
            }
            System.out.println("q size:" + q.size());
            synchronized (q) {
                while (q.size() > maxsize) {
                    System.out.println(Thread.currentThread().getName() + "producer await");
                    try {
                        //对象.wait()要放到对象加锁内部，如果不是对象的Monitor的拥有者，会报IllegalMonitorStateException
                        q.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    String msg = Thread.currentThread().getName() + "msg:" + (q.size() + 1);
                    q.add(msg);
                    System.out.println("producer:" + msg);
                    q.notifyAll();

                    Thread.sleep(RandomUtils.nextLong(100L, 500L));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer {
        private Queue q;

        public Consumer(Queue q) {
            this.q = q;
        }

        public void consumer() {
            while (true) {
                synchronized (q) {
                    if (q.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + "consumer await");
                        try {
                            q.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Object o = q.poll();
                        System.out.println(Thread.currentThread().getName() + "consumer:" + o);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new LinkedBlockingQueue<>();
        Producer p = new Producer(queue);
        Consumer c = new Consumer(queue);
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        p.produce();
                    }
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
