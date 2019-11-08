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
    }
}
