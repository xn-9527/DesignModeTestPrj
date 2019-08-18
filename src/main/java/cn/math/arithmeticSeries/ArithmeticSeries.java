package cn.math.arithmeticSeries;


import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/8/18.
 *
 * 等差数列
 */
@Slf4j
public class ArithmeticSeries {
    /**
     * 首项
     */
    private int a1;
    /**
     * 公差
     */
    private int step;

    public ArithmeticSeries(int a1, int step) {
        this.a1 = a1;
        this.step = step;
    }

    /**
     * 根据首项求第n项
     *
     * @param n
     * @return
     */
    public int f(int n) {
        if (n <= 1) {
            return a1;
        }
        return a1 + (n - 1) * step;
    }

    /**
     * 求首项到末项之和
     *
     * @param start
     * @param end
     * @return
     */
    public int sum(int start, int end) {
        Preconditions.checkArgument(start <= end, "开始项序号必须小于结束项");
        int am = f(start);
        int an = f(end);
        return (am + an) * (end - start + 1) / 2;
    }

    public static void main(String[] args) {
        ArithmeticSeries arithmeticSeries = new ArithmeticSeries(1, 1);
        for (int i = 1; i < 10 ; i ++) {
            log.info("第{}项{}",i ,arithmeticSeries.f(i));
        }
        int start = 3;
        int end = 6;
        log.info("从{}到{}总和{}", start, end, arithmeticSeries.sum(start, end));
    }
}
