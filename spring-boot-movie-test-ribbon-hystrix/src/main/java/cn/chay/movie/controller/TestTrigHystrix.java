package cn.chay.movie.controller;

import cn.chay.movie.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xiaoni on 2019/11/25.
 */
@Slf4j
public class TestTrigHystrix {
//    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        //模拟并发请求，5s内20次触发Hystrix熔断器
//        for (int i = 0; i < 20; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("" + restTemplate.getForObject("http://localhost:8010/movie/user/1", User.class));
//                }
//            }).start();
//        }
//    }
}
