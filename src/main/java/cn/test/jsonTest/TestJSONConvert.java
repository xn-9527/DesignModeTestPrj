package cn.test.jsonTest;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Blackknight
 * @Date: 2022/3/3 4:10 下午
 * @Description: 测试json转换可以数字 字母混合
 */
@Slf4j
public class TestJSONConvert {
    public static void main(String[] args) {
        List<String> l = JSON.parseObject("[1231234,'44234423']", List.class);

        Object o = (byte[])null;
        byte[] bytes = (byte[]) o;
        System.out.println(o == null);
        System.out.println(bytes == null);
        try {
            User cache = JSON.parseObject(bytes, User.class);
        } catch (Exception e) {
            log.error("test1 failed", e);
        }

        //----------------------------
        System.out.println("-------------------test2------------");
        Object o1 = ("null").getBytes();
        byte[] bytes1 = (byte[]) o1;
        System.out.println(o1 == null);
        System.out.println(bytes1 == null);
        try {
            User cache1 = JSON.parseObject(bytes1, User.class);
        } catch (Exception e) {
            log.error("test2 failed", e);
        }
    }
}
