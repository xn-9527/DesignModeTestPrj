package cn.chay.movie.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * feign自定义配置：禁用Hystrix
 * 如需为指定的feign客户端禁用hystrix，则只需在@FeignClient注解里的configuration配置该类即可
 * configuration = FeignDisableHystrixConfiguration.class
 *
 * Created by xiaoni on 2019/11/26.
 */
@Configuration
public class FeignDisableHystrixConfiguration {
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
