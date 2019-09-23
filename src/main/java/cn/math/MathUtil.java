package cn.math;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/8/9.
 */
@Slf4j
public class MathUtil {
    /**
     * 杨辉三角优化法-求解组合数 C(n,m) 从 n 里面 选 m 的总数
     *
     * @param n
     * @param k
     * @return
     */
    public static long C(int n, int k) {
        // N must be a positive integer.
        if (k < 0 || k > n) {
            throw new IllegalArgumentException("K must be an integer between 0 and N.");
        }
        k = (k > (n - k)) ? n - k : k;
        // C(n, 0) = 1, C(n, 1) = n
        if (k <= 1) {
            return k == 0 ? 1 : n;
        }
        int cacheLen = k + 1;
        long[] befores = new long[cacheLen];
        befores[0] = 1;
        long[] afters = new long[cacheLen];
        afters[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                afters[j] = befores[j - 1] + befores[j];
            }
            System.arraycopy(afters, 1, befores, 1, k);
        }
        return befores[k];
    }

    /**
     * A(m,k)从m个数中取k个数排序组合
     * 根据排列组合公式 A(m,n)/n!=C(m,n)
     *
     * @param m
     * @param n
     * @return
     */
    public static long A(int m, int n) {
        return C(m, n) * stepMultiply(n);
    }

    /**
     * n!
     *
     * @param n
     * @return
     */
    public static long stepMultiply(int n) {
        long result = 1;
        for(int i = 1; i <=n;i++) {
            result = result * i;
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 16;
        int k = 1;
        log.info("C({},{})={}",n,k, MathUtil.C(n, k));
        log.info("k!({})={}",k, MathUtil.stepMultiply(k));
        log.info("A({},{})={}",n,k, MathUtil.A(n, k));
        long loop = 0;
        for (int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j ++) {
                loop ++;
            }
        }
        log.info(loop + "");
    }

}
