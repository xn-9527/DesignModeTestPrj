package cn.test.threadsafty;

import java.util.concurrent.*;

/**
 * @Author: xiaoni
 * @Date: 2021/3/21 15:41
 */
public class CallableTest implements Callable<String> {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CallableTest callableTest = new CallableTest();
        Future<String> future = executorService.submit(callableTest);
        System.out.println("main thread before");
        //get方法是阻塞的
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("main thread finish");
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(2000L);
        System.out.println("in coming...");
        return "success";
    }
}
