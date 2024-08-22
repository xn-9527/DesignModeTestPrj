package cn.test.jsonTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/7/17.
 */
@Slf4j
public class JsonTest {

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 5, 3};
        JSONArray jsonArray = JSONObject.parseArray(JSON.toJSONString(a));
        log.info(JSON.toJSONString(jsonArray));
        Object[] c = jsonArray.toArray();
        log.info(JSON.toJSONString(c));
        for (int i = 0; i < c.length; i++) {
            Object o = c[i];
            log.info("第{}个元素：{}", i, o);
            int d = (int) c[i];
            log.info("第{}个元素：{}", i, d);
        }

        try {
            log.info(String.valueOf(JSON.parseArray("")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            log.info(String.valueOf(JSON.parseArray("[1,2,3]")));
            log.info(String.valueOf(JSON.parseArray("[]")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            log.info(String.valueOf(JSON.parseArray("1,2,3")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
