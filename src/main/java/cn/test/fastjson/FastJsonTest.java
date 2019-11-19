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
        /**
         * 测试@jsonField注解的字段，获取是用jsonField里的下划线还是用驼峰
         */
        JsonMissionDataPoint jsonMissionDataPoint = new JsonMissionDataPoint();
        jsonMissionDataPoint.setMapName("testMap");
        jsonMissionDataPoint.setSceneName("testScene");
        jsonMissionDataPoint.setPointName("testPoint");
        jsonMissionDataPoint.setX(1.2d);
        jsonMissionDataPoint.setY(1.3d);
        jsonMissionDataPoint.setTh(-12d);
        String jsonString = JSON.toJSONString(jsonMissionDataPoint);
        log.info("jsonString: {}", jsonString);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        log.info("非驼峰：" + jsonObject.getString("map_name"));
        log.info("驼峰：" + jsonObject.getString("mapName"));
    }
}
