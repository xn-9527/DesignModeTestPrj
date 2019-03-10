package cn.test.arrayCanChange;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by xiaoni on 2019/3/11.
 * 长度非0的数组总是可变的，所以即使是final static的，可能会引起安全漏洞
 * 有两种方法可以避免
 */
public class ArrayTest {
    private static final String[] PRIVATE_VALUES = {"a","b","c"};

    public static final String[] PRIVATE_VALUES_WRONG = {"c","b","a"};

    /**
     * 方法1：增加一个公有的不可变的列表
     */
    public static final List<String> VALUES =
            Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

    /**
     * 方法2：使数组变成私有的
     */
    public static final String[] values() {
        return PRIVATE_VALUES;
    }


    public static void main(String[] args) {
        /**
         * 演示可变
         */
        //打印cba
        System.out.println(Arrays.toString(ArrayTest.PRIVATE_VALUES_WRONG));
        Arrays.sort(ArrayTest.PRIVATE_VALUES_WRONG);
        //打印abc
        System.out.println(Arrays.toString(ArrayTest.PRIVATE_VALUES_WRONG));
    }
}
