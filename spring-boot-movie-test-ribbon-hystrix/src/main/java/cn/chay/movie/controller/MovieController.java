package cn.chay.movie.controller;

import cn.chay.movie.vo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Created by xiaoni on 2019/11/12.
 */
@RestController
@RequestMapping("/movie")
@Slf4j
public class MovieController {
    @Value("${user.userServiceUrl}")
    private String userServiceUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * @HystrixCommand 断路器配置，fallbackMethod回滚时调用的方法，@HystrixProperty设置commandProperties 属性
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "findByIdFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
    }, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "1"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
    },
    //ignoreExceptions 指定不想执行回退的异常类。
    //另外，Hystrix有个HystrixBadRequestException类，当该异常发生时，不会触发回退。所以如果我们业务抛出该异常，也不会回退。
    ignoreExceptions = {IllegalArgumentException.class, NullPointerException.class})
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        //硬编码url
//        User findOne = this.restTemplate.getForObject("http://localhost:8000/user/" + id, User.class);
        //配置文件url
//        User findOne = this.restTemplate.getForObject(userServiceUrl + id, User.class);
        //eureka和负载均衡器ribbon配合使用时，会自动将虚拟主机名映射成微服务的网络地址
        //默认服务名称与虚拟主机名一致，也可以使用eureka.instance.virtual-host-name或eureka.instance.secure-virtual-host-name指定虚拟主机名称
        //!!!虚拟主机名不能包含_等字符，否则ribbon在调用时会报异常
        User findOne = this.restTemplate.getForObject("http://microservice-user/user/" + id, User.class);
        return findOne;
    }

    @GetMapping("/log-user-instance")
    public void logUserInstance() {
        //loadBalancerClient.choose与restTemplate.getForObject不能写在同一个方法中，因为restTemplate已经加了@LoadBalanced注解，本身包含的choose的行为
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-user");
        //打印当前选择的是哪个节点
        log.info("###################{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
    }

    /**
     *
     * @param id
     * @param throwable 获取异常的原因，只需要加这个参数即可
     *
     * @return
     */
    public User findByIdFallback(Long id, Throwable throwable) {
        User user = new User();
        user.setId(-1L);
        user.setName("默认用户");
        return user;
    }
}
