package cn.test.java8.java8TryWithResources;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by xiaoni on 2018/12/25.
 */
public class AutoCloseTest {
    public static void main(String[] args) {
        args = new String[10];
        args[0] = "G:\\WorkSpaceSSD\\DesignModeTestPrj\\src\\main\\java\\cn\\test\\java8\\java8TryWithResources\\a.txt";
        args[1] = "G:\\WorkSpaceSSD\\DesignModeTestPrj\\src\\main\\java\\cn\\test\\java8\\java8TryWithResources\\b.txt";
        //a.txt里面inside和outside都打印出来了
        testNormalOutput(args[0]);
        /**
         * b.txt里面只打印了inside，outside报错，Stream Closed
         * 因为在testAutoCloseWithTryCatch方法里，global_out所指向的out对象已经在第一次try-catch之后被关闭了
         * 在第二次对这个已经关闭的流里输出内容时，就会报Stream Closed错误。
         */
        testAutoCloseWithTryCatch(args[1]);

        //测试try-with-resources工作原理
        testAutoClose();
    }

    private static void testNormalOutput(String filepath) {
        OutputStream global_out = null;
        BufferedWriter writer;
        try {
            OutputStream out = out = new FileOutputStream(filepath);
            global_out = out;
            out.write((filepath + "inside try catch block").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (global_out != null) {
                global_out.write("  \t\r outside try catch block".getBytes());
                global_out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testAutoCloseWithTryCatch(String filepath) {
        OutputStream global_out = null;
        //这里就是try with resources
        try (OutputStream out = new FileOutputStream(filepath);) {
            global_out = out;
            out.write((filepath + "inside try catch block").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (global_out != null) {
                global_out.write("  \t\r outside try catch block".getBytes());
                global_out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ============================================================
     * test how try-with-resources work
     * 会打印出如下结果
     * obj2 closing
     * obj1 closing
     * before finally close
     * obj1 closing
     * after finally close
     * java.lang.ArithmeticException: / by zero
     * at trywithresources.AutoCloseTest.testAutoClose(AutoCloseTest.java:60)
     * at trywithresources.AutoCloseTest.main(AutoCloseTest.java:12)
     * <p>
     * 从上述代码我们可以观测四件事
     * 1. 凡是实现了AutoCloseable接口的类，在try()里声明该类实例的时候，在try结束后，close方法都会被调用
     * 2. try结束后自动调用的close方法，这个动作会早于finally里调用的方法。
     * 3. 不管是否出现异常（int i=1/0会抛出异常），try()里的实例都会被调用close方法
     * 4. 越晚声明的对象，会越早被close掉。
     */
    private static void testAutoClose() {
        AutoCloseable global_obj1 = null;
        AutoCloseable global_obj2 = null;
        try (AutoCloseable obj1 = new AutoClosedImpl("obj1");
             AutoCloseable obj2 = new AutoClosedImpl("obj2");) {
            global_obj1 = obj1;
            int i = 1 / 0;
            global_obj2 = obj2;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("before finally close");
                if (global_obj1 != null) {
                    global_obj1.close();
                }
                if (global_obj2 != null) {
                    global_obj2.close();
                }
                System.out.println("after finally close");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class AutoClosedImpl implements AutoCloseable {
        private String name;

        public AutoClosedImpl(String name) {
            this.name = name;
        }

        @Override
        public void close() throws Exception {
            System.out.println(name + " closing");
        }
    }
}

