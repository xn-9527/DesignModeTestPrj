package cn.chay.user.controller;

import cn.chay.user.bean.User;
import cn.chay.user.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Created by xiaoni on 2019/11/12.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        User findOne = this.userRepository.findOne(id);
        return findOne;
    }

    @GetMapping("/get")
    public User get(User user) {
        return user;
    }

    @PostMapping("/post")
    public User post(@RequestBody User user) {
        return user;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) {
        String fileName = file.getName();
        log.info("{} file uploaded", fileName);
        return fileName;
    }
}
