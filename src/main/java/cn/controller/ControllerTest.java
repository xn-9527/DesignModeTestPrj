package cn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Created by chay on 2019/11/5.
 */
@Controller
@Slf4j
@RequestMapping("hello-world")
public class ControllerTest {

    @GetMapping
    public String getPage() {
        log.info("I'm get");
        return "hello";
    }

    /**
     * post返回页面不行，会报405，只能用重定向
     * @return
     */
    @PostMapping
    public String postPage() {
        log.info("I'm post");
        //直接返回报错,原因就是springMVC在返回前端ModelView的时候，做了supportedMethods校验，只支持GET和HEAD两种method。其余的一律报错。是框架返回modelView的要求。
        return "hello";
        //重定向url返回就可以
//        return "redirect:hello-world";
    }
}
