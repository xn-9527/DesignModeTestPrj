package cn.chay.movie.service;

import cn.chay.movie.vo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xiaoni on 2019/11/20.
 */
//name指定ribbon client负载均衡,使用@FeignClient的fallback属性指定回退类
@FeignClient(name = "microservice-user", fallback = FeignClientFallback.class)
public interface UserFeignClient {
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);
}

/**
 * 回退类需要实现Feign Client接口
 */
@Component
class FeignClientFallback implements UserFeignClient {
    @Override
    public User findById(Long id) {
        User user = new User();
        user.setId(-1L);
        user.setUsername("默认用户");
        return user;
    }
}