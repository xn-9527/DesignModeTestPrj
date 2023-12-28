package cn.test.forkjoin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * @Author: Blackknight
 * @Date: 2023/12/27 17:01
 * @Description:
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) {
        // 创建自定义的 ForkJoinPool
//        ForkJoinPool customForkJoinPool = new ForkJoinPool(4);
        //测试自定义名称
        ForkJoinPool customForkJoinPool = new ForkJoinPool(
                4,
                new CustomThreadFactory("myTestName"),
                null,
                false
        );

        // 使用自定义 ForkJoinPool 执行 parallelStream
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> result = customForkJoinPool.submit(() ->
                numbers.parallelStream()
                        .map(number -> process(number))
                        .collect(Collectors.toList())
        ).join();

        System.out.println("Result: " + result);

        // 关闭自定义 ForkJoinPool
        customForkJoinPool.shutdown();
    }

    private static int process(int number) {
        // 模拟耗时操作
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printThreadInfo();
        return number * 2;
    }

    /**
     * 打印 forkJoinPool 工作线程信息
     */
    private static void printThreadInfo() {
        ForkJoinPool pool = (ForkJoinPool) ForkJoinPool.commonPool();
        ForkJoinWorkerThread thread = (ForkJoinWorkerThread) Thread.currentThread();

        System.out.println("Thread ID: " + thread.getId());
        System.out.println("Thread Name: " + thread.getName());
        System.out.println("Thread Pool Name: " + pool.toString());
        System.out.println("---------------");
    }

    static class CustomThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {

        private final String poolName;

        public CustomThreadFactory(String poolName) {
            this.poolName = poolName;
        }

        @Override
        public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
            return new CustomForkJoinWorkerThread(pool, poolName);
        }
    }

    static class CustomForkJoinWorkerThread extends ForkJoinWorkerThread {

        private final String poolName;

        protected CustomForkJoinWorkerThread(ForkJoinPool pool, String poolName) {
            super(pool);
            this.poolName = poolName;
        }

        @Override
        protected void onStart() {
            super.onStart();
            setName(poolName + "-" + getPoolIndex());
        }
    }
}
