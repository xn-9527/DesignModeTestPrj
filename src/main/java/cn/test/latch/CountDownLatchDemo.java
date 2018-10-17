package cn.test.latch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xiaoni on 2017/3/15.
 * CountDownLatch是一个同步工具类,它允许一个或多个线程一直等待,直到其他线程的操作执行完后再执行。
 */
public class CountDownLatchDemo {
    final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(2);//两个工人的协作
        Worker worker1=new Worker("zhang san", 5000, latch);
        Worker worker2=new Worker("li si", 8000, latch);
        worker1.start();//
        worker2.start();//
        latch.await();//等待所有工人完成工作
        System.out.println("all work done at "+sdf.format(new Date()));
    }


    static class Worker extends Thread{
        String workerName;
        int workTime;
        CountDownLatch latch;
        public Worker(String workerName ,int workTime ,CountDownLatch latch){
            this.workerName=workerName;
            this.workTime=workTime;
            this.latch=latch;
        }

        @Override
        public void run(){
            try {
                System.out.println("Worker " + workerName + " do work begin at " + sdf.format(new Date()));
                doWork();//工作了
                System.out.println("Worker " + workerName + " do work complete at " + sdf.format(new Date()));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                /*
                * 使用 CountDownLatch 进行异步转同步操作，每个线程退出前必须调用 countDown
                方法，线程执行代码注意 catch 异常，确保 countDown 方法可以执行，避免主线程无法执行
                至 countDown 方法，直到超时才返回结果。
                * */
                latch.countDown();
            }
        }

        private void doWork() throws Exception{
            Thread.sleep(workTime);
        }
    }


}