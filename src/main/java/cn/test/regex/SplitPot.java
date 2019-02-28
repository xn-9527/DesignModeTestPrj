package cn.test.regex;

/**
 * Created by xiaoni on 2019/2/28.
 * 对于.java必需使用\来转义字符，\\才是\
 */
public class SplitPot {

    public static void main(String[] args) {
        String[] a = "a.b.c.d".split("\\.");
        System.out.println(a);
    }
}
