package cn.chay.movie.service;

import cn.chay.movie.config.FeignLogConfiguration;
import cn.chay.movie.vo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xiaoni on 2019/11/20.
 */
//name指定ribbon client负载均衡,为接口指定日志级别配置类
@FeignClient(name = "microservice-user", configuration = FeignLogConfiguration.class)
public interface UserFeignClient {
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);
}
