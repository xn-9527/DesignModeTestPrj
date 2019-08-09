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

    public static void main(String[] args) {
        int n = 9;
        int k = 2;
        log.info("" + MathUtil.C(n, k));
        long loop = 0;
        for (int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j ++) {
                loop ++;
            }
        }
        log.info(loop + "");
    }

}
