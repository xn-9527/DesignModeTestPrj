package cn;

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
		SpringApplication.run(Application.class, args);
		new ScheduleExecutorTest();
		logger.info("DesignModeTest SpringBoot Start Success");
		SpringContextHolder.getBean(TestApplicationContext.class).test();

		//注册一个关机钩，当系统被退出或被异常中断时，启动这个关机钩线程
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				//添入你想在退出JVM之前要处理的必要操作代码
				System.out.println("##################Main Thread Shutdown#####################");
			}
		});
	}

}
