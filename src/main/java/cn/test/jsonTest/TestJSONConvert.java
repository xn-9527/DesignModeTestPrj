package cn.test.jsonTest;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: x
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

        //-----------------------------
        System.out.println("-------------test3-----------------");
        String orinS = "fffffd";
        String js = JSON.toJSONString(orinS);
        byte[] b = JSON.toJSONBytes(orinS);
        byte[] bjs = js.getBytes(StandardCharsets.UTF_8);
        String bjDs = JSON.parseObject(js, String.class);
        log.info("json string {}", js);
        log.info("json byte string {}", b);
        log.info("json byte string2 {}", b.toString());
        log.info("json byte string3 {}", new String(b));
        log.info("json string byte string {}", new String(bjs));
        log.info("json string byte string equals {}", new String(bjs).equals(new String(b)));
        log.info("json string deSerialize {}", bjDs);
    }
}
