package cn.test.fastjson;

import cn.test.equals.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/10/23.
 */
@Slf4j
public class FastJsonTest {

    public static void main(String[] args) {
        //user1 = null,user2 = null
        try {
            User user = JSONObject.parseObject(JSONObject.toJSONString(""), User.class);
            log.info(JSON.toJSONString(user));
        } catch (Exception e) {
            log.error("empty string" + e.getMessage(), e);
        }
        try {
            User user2 = JSONObject.parseObject(JSONObject.toJSONString(null), User.class);
            log.info(JSON.toJSONString(user2));
        } catch (Exception e) {
            log.error("null" + e.getMessage(), e);
        }

    }
}
