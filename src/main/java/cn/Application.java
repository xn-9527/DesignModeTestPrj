package cn;

import cn.quartz.ScheduleExecutorTest;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@SpringBootApplication
//@ComponentScan
@EnableScheduling
//@EnableTransactionManagement
//@MapperScan("objectToMap.**.mapper")
public class Application {
	private static Logger logger = Logger.getLogger(Application.class);

	/**
	 * Start
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		new ScheduleExecutorTest();
		logger.info("DesignModeTest SpringBoot Start Success");
	}

}
