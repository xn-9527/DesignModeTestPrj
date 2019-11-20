package cn.chay.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * 示例：如果一些接口需要进行基于Http Basic的认证后才能调用，配置类的写法
 * Created by xiaoni on 2019/11/20.
 */
public class FooConfiguration {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "password");
    }
}
