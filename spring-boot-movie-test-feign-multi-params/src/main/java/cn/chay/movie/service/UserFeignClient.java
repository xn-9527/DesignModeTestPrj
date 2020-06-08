package cn.chay.movie.service;

import cn.chay.movie.vo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by xiaoni on 2019/11/20.
 */
//name指定ribbon client负载均衡
//注意MultipartSupportConfig 只能用于上传文件的表单提交，不能用于其他post请求的提交，会报错：feign.codec.EncodeException: class cn.chay.movie.vo.User is not a type supported by this encoder.
//@FeignClient(name = "microservice-user-multi-params")
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

    @RequestMapping(value = "/user/put", method = RequestMethod.PUT)
    public User put(@RequestBody User user);
}
