package cn.string2asc;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/9/23.
 * 字符串和ascii码互相转换
 */
@Slf4j
public class StringToAscUtil {
    public static String ConvertToASCII(String input) {
        StringBuilder sb = new StringBuilder();
        char[] ch = input.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            sb.append(Integer.valueOf(ch[i]).intValue()).append(" ");// 加空格
            // sb.append(Integer.valueOf(ch[i]).intValue());// 不加空格
        }
        return sb.toString();
    }

    /**
     * 形如49 57 51 53 50 49 46 54 52 49ascii转化成String
     * 空格分隔符
     *
     * @param input
     * @return
     */
    public static String ASCIIToConvert(String input) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] chars = input.split("\\ ");
        for (int i = 0; i < chars.length; i++) {
            stringBuffer.append((char) Integer.parseInt(chars[i]));
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        log.info(ASCIIToConvert("57 50 43 46"));
        log.info(ConvertToASCII("aefd"));
    }
}
