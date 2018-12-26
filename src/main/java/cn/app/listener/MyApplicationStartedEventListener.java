package cn.app.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot 启动监听类
 *
 * @author liaokailin,Chay
 * @version $Id: MyApplicationStartedEventListener.java, v 0.1 2015年9月2日 下午11:06:04 liaokailin Exp $
 */
public class MyApplicationStartedEventListener implements ApplicationListener<ApplicationStartingEvent> {

    private Logger logger = LoggerFactory.getLogger(MyApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        SpringApplication app = event.getSpringApplication();
        logger.info("==MyApplicationStartedEventListener==");
    }
}

