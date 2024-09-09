package cn.test.jsonTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Blackknight
 * @Date: 2024/9/4 18:31
 * @Description:
 */
@Slf4j
public class JsonNullColumnTest {
    public final static PropertyFilter JSON_FILTER = new PropertyFilter() {
        @Override
        public boolean apply(Object object, String name, Object value) {
            if (value == null) {
                return false;
            }
            if (value instanceof String && StringUtils.isEmpty((String) value)) {
                return false;
            }
            if (value instanceof Collection && CollectionUtils.isEmpty((Collection) value)) {
                return false;
            }
            if (value instanceof Map && MapUtils.isEmpty((Map) value)) {
                return false;
            }
            return true;
        }
    };
    public static void main(String[] args) {
        // 假设 result.getValue() 返回以下对象
        MyObject obj = new MyObject();
        obj.setField1(null);
        obj.setField2("");

        String jsonString = JSON.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse);
        System.out.println(jsonString);

        MyObject obj2 = new MyObject();
        obj2.setField1(null);
        obj2.setField2("");
        obj2.setField4(Collections.emptyList());
        System.out.println(JSON.toJSONString(obj2, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse));
        System.out.println(JSON.toJSONString(obj2, JSON_FILTER));

        System.out.println(obj2.getField4() instanceof Collection);

        Result result = new Result();
        result.setValue(Collections.emptyList());
        //不打印空结果日志
        boolean resultIsEmpty = isResultIsEmpty(result);
        log.info("resultIsEmpty:{}", resultIsEmpty);
        result.setValue(123);
        resultIsEmpty = isResultIsEmpty(result);
        log.info("resultIsEmpty2:{}", resultIsEmpty);
        result.setValue(new ArrayList<>());
        resultIsEmpty = isResultIsEmpty(result);
        log.info("resultIsEmpty3:{}", resultIsEmpty);
    }

    private static boolean isResultIsEmpty(Result result) {
        boolean resultIsEmpty = Objects.isNull(result) || Objects.isNull(result.getValue());
        if (!resultIsEmpty && result.getValue() instanceof Collection) {
            //直接对象数据是否为空判断
            resultIsEmpty = CollectionUtils.isEmpty((Collection) result.getValue());
        }
        return resultIsEmpty;
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

    @Data
    public static class Result {
        private Object value;
    }
}
