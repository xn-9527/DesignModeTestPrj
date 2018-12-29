package cn.app.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Created by chay on 2018/12/24.
 */
@Slf4j
public class MyApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        log.info("##########################MyApplicationFailedEventListener########################");
        Throwable throwable = event.getException();
        handleThrowable(throwable);
    }

    /**
     * 处理异常
     * 当设置虚拟机最大内存参数-Xmx20m的时候，启动会报错，进入该流程
     *
     * @param throwable
     */
    private void handleThrowable(Throwable throwable) {
        log.info("##########################app failed########################");
    }
}
