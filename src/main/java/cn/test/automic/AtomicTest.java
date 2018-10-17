package cn.test.automic;

/**
 * Created by xiaoni on 2018/4/4.
 */

import java.util.concurrent.atomic.AtomicInteger;

/**
 *Atomic变量自增运算测试
 *
 *@author
 */
public class AtomicTest{
    public static AtomicInteger race=new AtomicInteger(0);
    public static void increase(){
        race.incrementAndGet();
        System.out.println("加啦加啦" + race);
    }
    private static final int THREADS_COUNT=20;
    public static void main(String[]args)throws Exception{
        Thread[]threads=new Thread[THREADS_COUNT];
        for(int i=0;i<THREADS_COUNT;i++){
            int j = i;
            threads[i]=new Thread(new Runnable(){
                @Override
                public void run(){
                    for(int i=0;i<100;i++){
                        System.out.println("Thread" + j);
                        increase();
                    }
                }
            });
            threads[i].start();
        }
        while(Thread.activeCount()>2) {
            System.out.println(Thread.activeCount());
            //Yield告诉当前正在执行的线程把运行机会交给线程池中拥有相同优先级的线程。
            Thread.yield();
            System.out.println(race);
        }

    }
}
        /*运行结果如下：
        200000
        使用AtomicInteger代替int后，程序输出了正确的结果，一切都要归功于
        incrementAndGet()方法的原子性。它的实现其实非常简单，如代码清单13-5所示。
        代码清单13-5　incrementAndGet()方法的JDK源码
        */
