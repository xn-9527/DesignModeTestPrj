package cn.test.testYamlValue.impl;

import cn.test.testYamlValue.TestYamlValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Created by xiaoni on 2019/12/3.
 */
@Service
@Slf4j
public class TestYamlValueImpl implements TestYamlValueService {

    @Value("${test.param-a}")
    private Boolean paramA;
    @Value("${test.paramB}")
    private Boolean paramB;

    @Override
    public void testAB() {
        log.info("I am A: {}", paramA);
        log.info("I am B: {}", paramB);
    }
}
