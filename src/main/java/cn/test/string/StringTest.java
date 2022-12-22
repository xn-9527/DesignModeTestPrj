package cn.test.string;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Created by xiaoni on 2019/11/8.
 */
@Slf4j
public class StringTest {
    public static void main(String[] args) {
        String str = "abcdefg";
        log.info("" + str.length());
        log.info("截取最后一个字符串生成的新字符串为: " + str.substring(0,str.length()-1));

        String str1 = new StringBuilder("计算机").append("软件").toString();
        log.info("" + (str1.intern() == str1));
        String str2 = new StringBuilder("java").append("program").toString();
        log.info("" + (str2.intern() == str2));
        String str3 = new StringBuilder("美女好").toString();
        log.info("" + (str3.intern() == str3));

        String s1 = "张三";
        String s2 = new String("张三");
        log.info("" + (s1 == s2));


        log.info("测试startWith:" + "a_id".startsWith("a_"));
    }
}
