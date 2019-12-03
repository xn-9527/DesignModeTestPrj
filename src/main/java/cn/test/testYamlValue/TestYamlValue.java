package cn.test.testYamlValue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by xiaoni on 2019/12/3.
 */
@Service
@Slf4j
public class TestYamlValue {

    @Value("${test.param-a}")
    private Boolean paramA;
    @Value("${test.paramB}")
    private Boolean paramB;

    public void testAB() {
        log.info("I am A: {}", paramA);
        log.info("I am B: {}", paramB);
    }
}
