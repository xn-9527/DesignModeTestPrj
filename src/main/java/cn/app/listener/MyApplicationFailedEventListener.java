package cn.app.listener;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Created by chay on 2018/12/24.
 */
public class MyApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        Throwable throwable = event.getException();
        handleThrowable(throwable);
    }

    /**
     * 处理异常
     *
     * @param throwable
     */
    private void handleThrowable(Throwable throwable) {
        System.out.println("##########################app failed########################");

    }
}
