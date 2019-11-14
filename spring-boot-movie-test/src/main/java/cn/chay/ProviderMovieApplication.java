package cn.chay;

//import com.netflix.discovery.DiscoveryClient;
//import com.sun.jersey.api.client.filter.ClientFilter;
//import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by xiaoni on 2019/11/12.
 */
@SpringBootApplication
public class ProviderMovieApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //这个ClientFilter配置跟排除pom中springCloud中jersey冲突
    /**
     * 用自定义的ClientFilter去设置带权限的访问eureka server
     */
    /*@Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() {
        DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs = new DiscoveryClient.DiscoveryClientOptionalArgs();
        List<ClientFilter> additionalFilters = new ArrayList<>();
        additionalFilters.add(new HTTPBasicAuthFilter("user", "password123"));
        discoveryClientOptionalArgs.setAdditionalFilters(additionalFilters);
        return discoveryClientOptionalArgs;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(ProviderMovieApplication.class, args);
    }
}
