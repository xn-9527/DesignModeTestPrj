package cn.chay.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;

/**
 * feign配置
 * 该类可以不写@Configuration注解；如果加了@Configuration注解，则该类不可以放在应用程序上下文@ComponentScan扫描的包中
 *
 * Created by xiaoni on 2019/11/20.
 */
public class FeignConfiguration {
    /**
     * 将契约改成feign原生的默认契约，以便使用feign自带的注解
     * @return
     */
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }
}
