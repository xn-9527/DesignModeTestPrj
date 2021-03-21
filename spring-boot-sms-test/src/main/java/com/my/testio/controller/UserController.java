package com.my.testio.controller;

import com.my.testio.domain.User;
import com.my.testio.service.IUserService;
import com.my.testio.service.SmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: xiaoni
 * @Date: 2021/3/21 18:04
 */
@RestController
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private SmsClient smsClient;

    @PostMapping("/user")
    public String addUser(User user) {
        long start = System.currentTimeMillis();
        userService.insert(user);
        long end = System.currentTimeMillis();
        return "SUCCESS:" + (end - start);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @PostMapping("/sms/user")
    public String register(User user) {
        long start = System.currentTimeMillis();
        userService.insert(user);
        //同步发送短信，比较费时间
//        smsClient.sendSms("12345678");

        //异步化
        executorService.submit(() -> {
            smsClient.sendSms("12345678");
        });

        long end = System.currentTimeMillis();
        return "SUCCESS:" + (end - start);
    }
}
