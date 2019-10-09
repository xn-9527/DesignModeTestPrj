package cn.test.ping;

import cn.test.httpProgressBar.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xiaoni on 2019/10/9.
 */
@Service
@Slf4j
public class TestPingUtilService {
    @Autowired
    private RestTemplate restTemplate;

    public String test() {
        String result = PingUtil.connectAndCheckFirstUseGet(restTemplate,
                "http://www.baidu.com:80",
                "server",
                "http://www.baidu.com:80",
                String.class);
        log.info(result);
        return result;
    }
}
