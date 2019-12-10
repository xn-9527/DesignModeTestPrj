package cn.chay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
/**
 * zuul整合了Hystrix，整合了容错
 *
 * zuul与spring boot actuator配合使用时，zuul会暴露两个端点：
 * /route和filters。借助这些端点，可方便、直观地查看以及管理zuul。
 *
 * zuul已包含actuator监控功能
 */
@EnableZuulProxy
public class ZuulApplication {
  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  public static void main(String[] args) {
    SpringApplication.run(ZuulApplication.class, args);
  }
}
