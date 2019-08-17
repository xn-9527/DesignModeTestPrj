package cn.math.fibonacciSequence;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/8/17.
 * 斐波那契数列
 * <p>
 * 又称黄金分割数列、因数学家列昂纳多·斐波那契（Leonardoda Fibonacci）以兔子繁殖为例子而引入，故又称为“兔子数列”
 */
@Slf4j
public class FibonacciSequence {
    /**
     * 递归法求第n项
     *
     * @param n
     * @return
     */
    public static int f(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n >= 2) {
            int c = f(n - 1) + f(n - 2);
            return c;
        }
        return -1;
    }

    /**
     * 输出n项
     *
     * @param n
     * @return
     */
    public static int[] fFor(int n) {
        int a = 0;
        int b = 1;
        int c = 1;
        int[] result =new int[n];
        result[0] = a;
        result[1] = b;
        result[2] = c;
        for (int i = 3; i < n; i++) {
            a = b;
            b = c;
            c = a + b;
            result[i] = c;
        }
        log.info(JSON.toJSONString(result));
        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 40; i++) {
            log.info("a{} = {}", i, FibonacciSequence.f(i));
        }
        FibonacciSequence.fFor(40);
    }
}
