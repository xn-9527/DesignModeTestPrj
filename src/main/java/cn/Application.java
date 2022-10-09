package cn;

import cn.app.listener.MyApplicationEnvironmentPreparedEventListener;
import cn.app.listener.MyApplicationFailedEventListener;
import cn.app.listener.MyApplicationPreparedEventListener;
import cn.app.listener.MyApplicationStartedEventListener;
import cn.quartz.ScheduleExecutorTest;
import cn.test.applicationContext.SpringContextHolder;
import cn.test.applicationContext.TestApplicationContext;
import cn.test.runshell.RunShellUtil;
import org.apache.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.TimeZone;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@ComponentScan
@EnableScheduling
//@EnableTransactionManagement
//@MapperScan("objectToMap.**.mapper")
public class Application {
    private static Logger logger = Logger.getLogger(Application.class);

    //设置时区的方法二：在启动类加上——测试无效
    @PostConstruct
    void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    @Bean
    public SpringContextHolder springContextHolderUtil() {
        return new SpringContextHolder();
    }

    @Lazy
    @Bean
    public ScheduleExecutorTest scheduleExecutorTest() {
        return new ScheduleExecutorTest();
    }

    /**
     * Start
     */
    public static void main(String[] args) {
        //设置时区的方法一——测试有效
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));

        //测试监听器的启动方式,该方式不会打印banner
//        startAppWithListener(args);

        //原默认启动方式，该方式可以打印banner，可以设置bannerMode(Banner.Mode.OFF).run(args)来关闭
		SpringApplication.run(Application.class, args);

        //测试定时任务
//        new ScheduleExecutorTest();

        logger.info("DesignModeTest SpringBoot Start Success");

        //测试获取上下文工具
        SpringContextHolder.getBean(TestApplicationContext.class).test();
        //测试定时任务
        SpringContextHolder.getBean(ScheduleExecutorTest.class);
        //测试通过反射获取bean调用方法
        Object bean = SpringContextHolder.getBean("testApplicationContext");
        logger.info("testApplicationContext bean:{}" + bean);
        Class selectsClass = bean.getClass();
        logger.info("testApplicationContext class:{}" + selectsClass);
        try {
            Method method = selectsClass.getMethod("test2", null);
            logger.info("testApplicationContext method:{}" + method);
            Object result = method.invoke(bean, null);
            logger.info("testApplicationContext result:{}" + result);

            Method method3 = selectsClass.getMethod("test3", new Class[] {Integer.class});
            logger.info("testApplicationContext method:{}" + method3);
            Object result3 = method3.invoke(bean, 12345);
            logger.info("testApplicationContext result:{}" + result3);
        } catch (Exception e) {
            logger.error("testApplicationContext error" ,e);
        }


        /**
         * 经测试，springboot的shutdownHook无效
         */
        //注册一个关机钩，当系统被退出或被异常中断时，启动这个关机钩线程
//        addShutdownHook(args);

        //尝试让主线程异常,亲测这两种不会导致jvm挂掉
        /*//java.lang.ArithmeticException: / by zero
        System.out.println(1 / 0);
        //OutOfMemoryError
        List<String> test = new ArrayList<>();
        while (1==1) {
            test.add("asdfsdf");
        }*/

        //退出码，钩子有效
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
//        logger.info("30秒到了，开始退出虚拟机");
//        System.exit(1);
    }

    /**
     * 设置一个关机钩子，在jvm异常退出前会执行
     */
    private static void addShutdownHook(final String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                //添入你想在退出JVM之前要处理的必要操作代码
                System.out.println("##################Main Thread Shutdown#####################");
                RunShellUtil.runShell("G:\\WorkSpaceSSD\\DesignModeTestPrj\\src\\main\\java\\cn\\test\\runshell\\demo-classes.bat");
//                addShutdownHook(args);
                //尝试在关机后，重新运行spring,会报错shutting down in progress
//                startAppWithListener(args);
            }
        });
    }

    /**
     * 带监听的启动项目
     *
     * @param args
     */
    private static void startAppWithListener(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new MyApplicationStartedEventListener());
        app.addListeners(new MyApplicationFailedEventListener());
        app.addListeners(new MyApplicationPreparedEventListener());
        app.addListeners(new MyApplicationEnvironmentPreparedEventListener());
        //无论怎么设置，banner不会打印
        app.setBannerMode(Banner.Mode.CONSOLE);
        app.run(args);
    }
}
