package cn.test.jsonTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.util.List;

/**
 * @Author: Blackknight
 * @Date: 2024/9/4 18:31
 * @Description:
 */
public class JsonNullColumnTest {

    public static void main(String[] args) {
        // 假设 result.getValue() 返回以下对象
        MyObject obj = new MyObject();
        obj.setField1(null);
        obj.setField2("value2");

        String jsonString = JSON.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse);
        System.out.println(jsonString);
    }

    @Data
    public static class MyObject {
        private String field1;
        private String field2;
        private List<String> field4;
        private Integer field5;
        private Boolean field6;

        // getters and setters
    }
}
