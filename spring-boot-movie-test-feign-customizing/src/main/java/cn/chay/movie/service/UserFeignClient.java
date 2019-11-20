package cn.chay.movie.service;

import cn.chay.config.FeignConfiguration;
import cn.chay.movie.vo.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xiaoni on 2019/11/20.
 */
//name指定ribbon client负载均衡,configuration指定配置类
@FeignClient(name = "microservice-user", configuration = FeignConfiguration.class)
public interface UserFeignClient {
    /**
     * 使用feign自带的注解@RequestLine代替@RequestMapping,@Param代替@PathVariable
     * @see https://github.com/OpenFeign/feign
     *
     * @param id
     * @return
     */
    //    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @RequestLine("GET /user/{id}")
    public User findById(@Param("id") Long id);
}
