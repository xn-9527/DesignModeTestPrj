package cn.soulutions.steps;

import java.util.Arrays;

/**
 * 有 N 级的台阶，你一开始在底部，每次可以向上迈最多 K 级台阶（最少 1 级），问到达第 N 级台阶有多少种不同方式。
 * 输入
 * 多组输入,两个正整数N(N ≤ 1000)，K(K ≤ 100)。
 * 输出
 * 一个正整数，为不同方式数，由于答案可能很大，你需要输出 ans mod 100003 后的结果。
 * 样例输入
 * 5 2
 * 样例输出
 * 8
 *
 * @author Chay
 * @date 2020/3/20 21:00
 */
public class StepsTest {
    public static void main(String[] args) {
        int n = 5;
        int k = 2;
        //因为a[0]=1是额外的条件，所以空间申请要多一个
        int[] a = new int[n+1];

        a[0] = 1;
        /**
         * 从end往前推，start在k步内的所有情况。
         * K等于2时的递推公式：
         * F（N）=1，N=0或1;F(N)=F(N-1)+F(N-2);
         * 这道题目就是斐波那契数列的变形，以上是K等于2时的递推公式，我们很容易类比，归纳出当K的值不确定时候的递推公式：
         * F（N）=1，N=0；F(N)=F(N-1)+F(N-2)+······+F(N-K）。注意N-K不能小于0 。
         */
        for (int end = 1; end <= n; end++) {
            /**
             * 依次计算F(N-1)到F(N-k)的值，并累加后取模，一般取模是为了防止溢出
             */
            for (int start = 1; start <= k && start <= end; start++) {
                a[end] = (a[end] + a[end - start]) % 100003;
            }
        }
        System.out.println(a[n]);
        System.out.println(Arrays.toString(a));
    }
}
