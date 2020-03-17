package cn.isPowerOf2;

/**
 * 是否是2的幂次
 *
 * 2的幂次特点：
 * n转换成二进制是 10000
 * n-1再转换成二进制是 1111
 * n&(n-1)=0
 *
 * @Author: xiaoni
 * @Date: 2020/3/17 19:10
 */
public class IsPowerOf2Test {
    public static boolean isPowerOf2(int num) {
        return (num & (num -1)) == 0;
    }

    public static void main(String[] args) {
        System.out.println("100是否是2的幂次:" + isPowerOf2(100));
        System.out.println("8是否是2的幂次:" + isPowerOf2(8));
    }
}
