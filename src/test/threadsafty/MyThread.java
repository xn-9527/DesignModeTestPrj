package test.threadsafty;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xiaoni on 2018/4/2.
 */
class MyThread implements Runnable {
    private List<Object> list;

    private CountDownLatch countDownLatch;

    public MyThread(List<Object> list, CountDownLatch countDownLatch)
    {
        this.list = list;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run()
    {
        // 每个线程向List中添加100个元素
        for(int i = 0; i < 100; i++)
        {
            try {
                list.add(new Object());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 完成一个子线程
        countDownLatch.countDown();
    }
}
