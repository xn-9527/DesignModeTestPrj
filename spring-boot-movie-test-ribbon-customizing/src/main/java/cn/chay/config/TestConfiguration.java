package cn.chay.config;

import cn.chay.annotation.ExcludeFromComponentScan;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * 使用ribbonClient，为特定name的Ribbon Client自定义配置
 * 使用@RibbonClient的configuration属性，指定Ribbon的配置类
 * 参考：http://spring.io/guides/gs/client-side-load-balancing/
 * @author Created by xiaoni on 2019/11/15.
 */
@Configuration
@RibbonClient(name = "microservice-user", configuration = RibbonConfiguration.class)
@ExcludeFromComponentScan
public class TestConfiguration {
}
