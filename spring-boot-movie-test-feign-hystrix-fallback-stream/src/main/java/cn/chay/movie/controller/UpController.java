package cn.chay.movie.controller;

import cn.chay.movie.service.MyHealthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by xiaoni on 2019/11/14.
 */
@RestController
public class UpController {
    @Autowired
    MyHealthChecker myHealthChecker;

    @RequestMapping("/up")
    public String up(@RequestParam("up") Boolean up) {
        myHealthChecker.setUp(up);

        return up.toString();
    }

}
