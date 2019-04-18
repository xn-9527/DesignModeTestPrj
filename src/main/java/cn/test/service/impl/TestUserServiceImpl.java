package cn.test.service.impl;

import cn.test.service.TestUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by xiaoni on 2019/4/18.
 */
@Slf4j
@Service
public class TestUserServiceImpl implements TestUserService {

    @Override
    public void sayHello() {
        log.info("Hello, I'm chay!");
    }
}
