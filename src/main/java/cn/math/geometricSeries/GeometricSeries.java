package cn.math.geometricSeries;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/8/18.
 *
 * 等比数列
 */
@Slf4j
public class GeometricSeries {
    /**
     * 首项
     */
    private long a1;
    /**
     * 比值
     */
    private long step;

    public GeometricSeries(long a1, long step) {
        this.a1 = a1;
        this.step = step;
    }

    /**
     * 求第n项
     *
     * @param n
     * @return
     */
    public long f(int n) {
        return Math.round(a1 * Math.pow(step, n-1));
    }

    /**
     * 求从start到end项之和
     *
     * @param start
     * @param end
     * @return
     */
    public long sum(int start, int end) {
        Preconditions.checkArgument(start <= end, "开始项序号必须小于结束项");
        long am = f(start);
        long an = f(end);
        if (step == 1) {
            return a1 * (end - start + 1);
        }
        return am + (am - an) * step / (1 - step);
    }

    public static void main(String[] args) {
        GeometricSeries geometricSeries = new GeometricSeries(1, 2);
        for (int i = 1; i < 10 ; i ++) {
            log.info("第{}项{}",i ,geometricSeries.f(i));
        }
        int start = 4;
        int end = 8;
        log.info("从{}到{}总和{}", start, end, geometricSeries.sum(start, end));
    }
}
