package cn.chay;

//import com.netflix.discovery.DiscoveryClient;
//import com.sun.jersey.api.client.filter.ClientFilter;
//import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @EnableCircuitBreaker 或者 @EnableHystrix 启动熔断器
 *
 * 试了下启动hystrix-dashboard,然后访问localhost:8030，添加localhost:8031并不能解析监控数据。
 *
 * @author Created by xiaoni on 2019/11/12.
 */
@SpringBootApplication
@EnableCircuitBreaker
public class ProviderMovieApplication {
    @Bean
    //整合ribbon的负载均衡
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProviderMovieApplication.class, args);
    }
}
