package cn.chay.movie.service;

import cn.chay.movie.config.FeignDisableHystrixConfiguration;
import cn.chay.movie.vo.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xiaoni on 2019/11/20.
 */
/**
 * name指定ribbon client负载均衡,使用@FeignClient的fallbackFactory属性指定回退类，注意有个Factory，否则会报错
 * Incompatible fallback instance. Fallback/fallbackFactory of type class cn.chay.movie.service.FeignClientFallbackFactory is not assignable to interface
 */
@FeignClient(name = "microservice-user", fallbackFactory = FeignClientFallbackFactory.class)
//测试禁用feign自带的hystrix
//@FeignClient(name = "microservice-user", configuration = FeignDisableHystrixConfiguration.class)
public interface UserFeignClient {
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);
}

/**
 * UserFeignClient的fallbackFactory类，该类需实现FallbackFactory接口，并覆写create方法
 * The fallback factory must produce instances of fallback classes that
 * implement the interface annotated by {@link FeignClient}.
 * @author 周立
 */
@Component
@Slf4j
class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable cause) {
        return new UserFeignClient() {
            @Override
            public User findById(Long id) {
                // 日志最好放在各个fallback方法中，而不要直接放在create方法中。
                // 否则在引用启动时，就会打印该日志。
                // 详见https://github.com/spring-cloud/spring-cloud-netflix/issues/1471
                log.info("fallback; reason was:", cause);
                User user = new User();
                //可以根据不同异常返回不同的回退结果
                if (cause instanceof IllegalArgumentException) {
                    user.setId(-2L);
                } else {
                    user.setId(-1L);
                }

                user.setUsername("默认用户");
                return user;
            }
        };
    }
}
