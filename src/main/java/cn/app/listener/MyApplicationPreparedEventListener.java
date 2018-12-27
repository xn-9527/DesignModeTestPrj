package cn.app.listener;

import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 上下文创建完成后执行的事件监听器
 *
 * @author liaokailin,Chay
 * @version $Id: MyApplicationPreparedEventListener.java, v 0.1 2015年9月2日 下午11:29:38 liaokailin Exp $
 */
@Slf4j
public class MyApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {
//    private Logger logger = LoggerFactory.getLogger(MyApplicationPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        log.info("###MyApplicationPreparedEventListener###");
        ConfigurableApplicationContext cac = event.getApplicationContext();
        passContextInfo(cac);
    }

    /**
     * 传递上下文
     * @param cac
     */
    private void passContextInfo(ApplicationContext cac) {
        //dosomething()
    }

}

