package cn.test.JVMQuit;

/**
 * Created by chay on 2018/7/14.
 */
public class TestJVMQuit {

    public static void main(String[] args) {
        System.out.println("my java process");

        //在注册关机钩前，异常退出，不会执行关机钩
//        System.out.println(1/0);

        //注册一个关机钩，当系统被退出或被异常中断时，启动这个关机钩线程
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                //添入你想在退出JVM之前要处理的必要操作代码
                System.out.println("T1");}
        });

        //注册第二个关机钩
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){ System.out.println("T2");}
        });

        //在注册关机钩后，异常退出，会执行关机钩
//        System.out.println(1/0);

        //正常退出会执行关机钩，不加这句，虚拟机也会正常退出，执行关机钩
//        System.exit(0);
    }
}
