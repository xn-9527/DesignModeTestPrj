package cn.chay.config;

import cn.chay.annotation.ExcludeFromComponentScan;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该类为ribbon的配置类
 * 注意：不应该在主应用程序上下文的@ComponentScan所扫描的包中
 *
 * @author Created by xiaoni on 2019/11/15.
 */
@Configuration
@ExcludeFromComponentScan
public class RibbonConfiguration {
    @Bean
    public IRule ribbonRule() {
        //负载均衡规则，改成随机
        return new RandomRule();
    }
}
