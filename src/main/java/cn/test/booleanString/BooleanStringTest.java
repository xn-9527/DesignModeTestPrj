package cn.test.booleanString;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by xiaoni on 2019/1/2.
 */
public class BooleanStringTest {
    public static void main(String[] args) {
        System.out.println(String.valueOf(true));

        JSONObject jsonObject = new JSONObject();
        String key = "a";
        String key1 = "b";
        jsonObject.put(key, true);
        jsonObject.put(key1, "1");

        printJsonObject(jsonObject, key);
        printJsonObject(jsonObject, key1);
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
