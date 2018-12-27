package cn;

import cn.app.listener.MyApplicationEnvironmentPreparedEventListener;
import cn.app.listener.MyApplicationFailedEventListener;
import cn.app.listener.MyApplicationPreparedEventListener;
import cn.app.listener.MyApplicationStartedEventListener;
import cn.quartz.ScheduleExecutorTest;
import cn.test.applicationContext.SpringContextHolder;
import cn.test.applicationContext.TestApplicationContext;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
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

    /**
     * Start
     */
    public static void main(String[] args) {
        //设置时区的方法一——测试有效
//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));

        //测试监听器的启动方式
        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new MyApplicationStartedEventListener());
        app.addListeners(new MyApplicationFailedEventListener());
        app.addListeners(new MyApplicationPreparedEventListener());
        app.addListeners(new MyApplicationEnvironmentPreparedEventListener());
        app.run(args);

        //原默认启动方式
//		SpringApplication.run(Application.class, args);

        //测试定时任务
        new ScheduleExecutorTest();

        logger.info("DesignModeTest SpringBoot Start Success");

        //测试获取上下文工具
        SpringContextHolder.getBean(TestApplicationContext.class).test();

        /**
         * 经测试，springboot的shutdownHook无效
         */
        //注册一个关机钩，当系统被退出或被异常中断时，启动这个关机钩线程
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                //添入你想在退出JVM之前要处理的必要操作代码
                System.out.println("##################Main Thread Shutdown#####################");
            }
        });

        //尝试让主线程异常
        System.out.println(1 / 0);
    }

}
