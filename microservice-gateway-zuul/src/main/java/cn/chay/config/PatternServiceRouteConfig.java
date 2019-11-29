package cn.chay.config;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiaoni on 2019/11/28.
 */
@Configuration
public class PatternServiceRouteConfig {
    /**
     * 实现从微服务到映射路由的正则配置
     * http://localhost:8040/routes 查看路由
     * {
        "/microservice-movie-ribbon/**": "microservice-movie-ribbon",
        "/v1/microservice-user/**": "microservice-user-v1",
        "/microservice-movie-ribbon-hystrix/**": "microservice-movie-ribbon-hystrix"
     }
     *
     * @return
     */
    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        //调用构造函数PatternServiceRouteMapper(String servicePattern, String routePattern)
        //servicePattern 指定微服务的正则，routePattern指定路由正则
        //通过下面的正则，可以把microservice-user-v1的服务映射到v1/microservice-user/**这个路径
        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)",
                "${version}/${name}");
    }
}
