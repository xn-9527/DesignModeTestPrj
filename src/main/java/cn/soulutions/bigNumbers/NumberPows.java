package cn.soulutions.bigNumbers;

import java.math.BigDecimal;

/**
 * 腾讯题(20分钟)：2^2000次方是多少
 *
 * @author Chay
 * @date 2021/6/25 15:07
 */
public class NumberPows {

    public static void main(String[] args) {
        System.out.println(2 << 1);
        System.out.println(2 << 10);
        System.out.println(2 << 20);
        System.out.println(1 << 10);
        System.out.println(1 << 20);
        System.out.println(Math.pow(2, 10));
        System.out.println(Math.pow(2, 20));
        BigDecimal base = new BigDecimal("2");
        System.out.println(base.pow(2));
        System.out.println(base.pow(10));
        /**
         * BigDecimal, n为幂次
         * 先计算scale * n保留几位小数是否超范围，默认scale=0
         */
        System.out.println(base.pow(2000));
    }
}
