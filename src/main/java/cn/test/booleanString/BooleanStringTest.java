package cn.test.booleanString;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoni on 2019/1/2.
 */
public class BooleanStringTest {
    public static void main(String[] args) {
        System.out.println(String.valueOf(true));

        JSONObject jsonObject = new JSONObject();
        String key = "a";
        String key1 = "b";
        String key2 = "c";
        jsonObject.put(key, true);
        jsonObject.put(key1, "1");

        printJsonObject(jsonObject, key);
        printJsonObject(jsonObject, key1);

        System.out.println("#############################################");
        Map map = new HashMap();
        map.put(key, true);
        map.put(key1,"1");
        map.put(key2,1);
        System.out.println(map);
        System.out.println(Boolean.valueOf(map.get(key).toString()).booleanValue());
        System.out.println(Boolean.valueOf(map.get(key1).toString()).booleanValue());
        System.out.println(Boolean.valueOf(map.get(key2).toString()).booleanValue());
        System.out.println(Boolean.parseBoolean(map.get(key1).toString()));
        System.out.println(Boolean.valueOf("1".equals(map.get(key1))));
        System.out.println(String.valueOf("1".equals(map.get(key1))));
        System.out.println(String.valueOf(map.get(key2).equals("1")));
        System.out.println(String.valueOf(map.get(key2).equals(1)));
        System.out.println(Boolean.TRUE.toString());
        System.out.println(Boolean.TRUE.toString().toLowerCase().equals(map.get(key).toString()));
        System.out.println(Boolean.TRUE.toString().toLowerCase().equals(map.get(key1).toString()));
        System.out.println(Boolean.TRUE.toString().toLowerCase().equals(map.get(key2).toString()));
    }

    /**
     * 打印jsonObject的key值
     *
     * @param jsonObject
     * @param key
     */
    public static void printJsonObject(JSONObject jsonObject, String key) {
        Object a = jsonObject.get(key);
        System.out.println(a);
        Boolean b = jsonObject.getBoolean(key);
        System.out.println(b);
    }
}
