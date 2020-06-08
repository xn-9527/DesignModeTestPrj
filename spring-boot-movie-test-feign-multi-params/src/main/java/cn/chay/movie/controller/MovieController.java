package cn.chay.movie.controller;

import cn.chay.movie.service.FileUploadFeignClient;
import cn.chay.movie.service.UserFeignClient;
import cn.chay.movie.vo.User;
import com.netflix.discovery.converters.Auto;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.StringDecoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by xiaoni on 2019/11/12.
 */
@RestController
@RequestMapping("/movie")
public class MovieController {
//    @Autowired
    private UserFeignClient userFeignClient;
//    @Autowired
    private FileUploadFeignClient fileUploadFeignClient;

    /**
     * @FeignClient同一个name使用多个配置类的解决方案 - 简书  https://www.jianshu.com/p/634011bbfab6
     *
     *
     * Feign有一个局限性，即对于同一个service-id只能使用一个配置类，
     * 如果有多个@FeignClient注解使用了相同的name属性，则注解的configuration参数会被覆盖。
     * 至于谁覆盖谁要看Spring容器初始化Bean的顺序。
     * 这个问题的有效解决方案是，当你需要给一个service-id配置第二个@FeignClient时，
     * 使用Feign Builder API手动创建接口代理，而不是通过注解：
     *
     * @param client
     */
    @Autowired
    public MovieController(Client client) {
        this.userFeignClient = Feign.builder().client(client)
                .encoder(new SpringEncoder(new ObjectFactory<HttpMessageConverters>() {
                    @Override
                    public HttpMessageConverters getObject() throws BeansException {
                        return new HttpMessageConverters(new GsonHttpMessageConverter());
                    }
                }))
                .decoder(new SpringDecoder(new ObjectFactory<HttpMessageConverters>() {
                    @Override
                    public HttpMessageConverters getObject() throws BeansException {
                        return new HttpMessageConverters(new GsonHttpMessageConverter());
                    }
                }))
                .contract(new SpringMvcContract())
                .target(UserFeignClient.class, "http://microservice-user-multi-params");
        this.fileUploadFeignClient = Feign.builder().client(client)
                .encoder(new SpringFormEncoder())
                .contract(new SpringMvcContract())
                .target(FileUploadFeignClient.class, "http://microservice-user-multi-params");
    }

    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        return this.userFeignClient.findById(id);
    }

    @GetMapping("/user/get1")
    public User get1(@RequestParam Long id, @RequestParam String username) {
        return this.userFeignClient.get1(id, username);
    }

    @GetMapping("/user/get2")
    public User get2(@RequestParam Long id, @RequestParam String username) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("username", username);
        return this.userFeignClient.get2(map);
    }

    @PostMapping("user/post")
    public User post(@RequestBody User user) {
        return this.userFeignClient.post(user);
    }

//    @PutMapping("user/put")
//    public User put(@RequestBody User user) {
//        return this.userFeignClient.put(user);
//    }

    @PostMapping("user/upload")
    public String post(@RequestParam MultipartFile file) {
        return this.fileUploadFeignClient.fileUpload(file);
    }
}
