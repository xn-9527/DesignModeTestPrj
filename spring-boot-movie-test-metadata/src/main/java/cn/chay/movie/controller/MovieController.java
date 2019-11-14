package cn.chay.movie.controller;

import cn.chay.movie.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Created by xiaoni on 2019/11/12.
 */
@RestController
@RequestMapping("/movie")
public class MovieController {
    @Value("${user.userServiceUrl}")
    private String userServiceUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        //硬编码url
//        User findOne = this.restTemplate.getForObject("http://localhost:8000/user/" + id, User.class);
        //配置文件url
        User findOne = this.restTemplate.getForObject(userServiceUrl + id, User.class);
        return findOne;
    }

    /**
     * 查询user service信息
     * @return
     */
    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo() {
        return this.discoveryClient.getInstances("microservice-user-metadata");
    }
}
