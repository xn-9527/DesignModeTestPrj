package cn.test.ping;

import cn.test.httpProgressBar.AjaxResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Created by xiaoni on 2019/10/9.
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

    public void testAjaxResult() {
        int totalFailed = 0;
        int total = 10000;
        for (int i = 0; i < total; i++) {
            AjaxResult result = PingUtil.serverConnectAndCheckFirstUseTokenHttpMethod(restTemplate,
                    HttpMethod.GET,
                    "4b9e1388-8d47-4119-a368-c7f6dbd1fd23",
                    "http://127.0.0.1:8063/area/station",
                    null,
                    AjaxResult.class);
            log.info("time {} start", i);
            if (result == null || !result.isSuccess() || result.getData() == null) {
                totalFailed ++;
                log.error("time {}, failed, total-Failed {} , total {}", i, totalFailed, total);
            } else {
                PageInfo<?> pageInfo = JSON.parseObject(JSON.toJSONString(result.getData()), PageInfo.class);
                if (pageInfo.getList() == null || pageInfo.getList().isEmpty()) {
                    totalFailed ++;
                    log.error("time {}, result list empty, total-Failed {} , total {}", i, totalFailed, total);
                }
            }
        }
        log.info("test finished, result: total-Failed {}, total {}", totalFailed, total);
    }
}
