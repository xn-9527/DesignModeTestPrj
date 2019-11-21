package cn.chay.movie.controller;

import cn.chay.movie.service.UserFeignClient;
import cn.chay.movie.vo.User;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private UserFeignClient userFeignClient;

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

    @PostMapping("user/upload")
    public String post(@RequestParam MultipartFile file) {
        return this.userFeignClient.fileUpload(file);
    }
}
