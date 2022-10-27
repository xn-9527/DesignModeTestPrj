package cn.test.volatileTest;

/**
 * @Author: x
 * @Date: 2022/7/28 3:09 下午
 * @Description: volatile测试
 */
public class TestVolatile2 {
    volatile boolean stop = false;
//    boolean stop = false;

    public void test() {
        System.out.println("started");
        while (!stop) {

        }
        System.out.println("stopped");
    }

    public static void main(String[] args) {
        TestVolatile2 test7 = new TestVolatile2();
        Thread t1 = new Thread(()-> test7.test());
        t1.start();
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test7.stop = true;
    }
}
