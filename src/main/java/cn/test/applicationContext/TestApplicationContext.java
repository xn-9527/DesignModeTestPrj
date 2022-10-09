package cn.test.applicationContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Created by xiaoni on 2018/12/11.
 */
@Component("testApplicationContext")
@Slf4j
public class TestApplicationContext {
    public void test() {
        log.info("dsfsdfsdfasdf");
    }

    public int test2() {
        log.info("aaaaaabvbbbvvvv");
        return -10086;
    }

    public int test3(Integer input) {
        log.info("cccccccccccccc");
        return input;
    }
}
