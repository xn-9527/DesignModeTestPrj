package cn.app.listener;

import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot 启动监听类
 *
 * @author liaokailin, Chay
 * @version $Id: MyApplicationStartedEventListener.java, v 0.1 2015年9月2日 下午11:06:04 liaokailin Exp $
 */
@Slf4j
public class MyApplicationStartedEventListener implements ApplicationListener<ApplicationStartingEvent> {

//    private Logger logger = LoggerFactory.getLogger(MyApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println("####MyApplicationStartedEventListener####");
        log.info("####MyApplicationStartedEventListener####");
        SpringApplication app = event.getSpringApplication();
        //不显示banner信息
        app.setBannerMode(Banner.Mode.OFF);
    }
}

