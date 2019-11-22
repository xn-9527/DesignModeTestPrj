package cn.chay.movie.service;

import cn.chay.config.MultipartSupportConfig;
import cn.chay.movie.vo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by xiaoni on 2019/11/20.
 */
//name指定ribbon client负载均衡
@FeignClient(name = "microservice-user-multi-params", configuration = MultipartSupportConfig.class)
public interface UserFeignClient {
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);

    /**
     * get多参数第一种方法，有多少写多少
     *
     * @param id
     * @param username
     * @return
     */
    @RequestMapping(value = "/user/get", method = RequestMethod.GET)
    public User get1(@RequestParam("id") Long id, @RequestParam("username") String username);

    /**
     * get多参数的第二种方法，用Map构建
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/user/get", method = RequestMethod.GET)
    public User get2(@RequestParam Map<String, Object> map);

    @RequestMapping(value = "/user/post", method = RequestMethod.POST)
    public User post(@RequestBody User user);

    @RequestMapping(value = "/user/upload", method = RequestMethod.POST,
        //这两个配置不能少
        produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //注意这里的注解是@RequestPart，而不是@RequestParam
    //因为文件大的时候，上传时间长，所以需要将Hystrix的超时时间设置长一些
    public String fileUpload(@RequestPart(value = "file")MultipartFile file);
}
