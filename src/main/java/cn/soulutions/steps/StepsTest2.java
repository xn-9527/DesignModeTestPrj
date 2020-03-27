package cn.soulutions.steps;

/**
 * 走阶梯，有n级阶梯，一次可以走一级、两级或者三级，请编写一个函数计算走完该阶梯一共有多少种种方法。
 *
 * Created by xiaoni on 2020/3/26.
 */
public class StepsTest2 {
    public static void main(String[] args) {
        System.out.println(calSteps(5));
    }

    /**
     * f(1)=1; f(2)=2;f(3)=4;
     * f(n) = f(n-1) + f(n-2) + f(n-3), n>3
     */
    public static int calSteps(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (n ==3) {
            return 4;
        }
        return calSteps(n-1) + calSteps(n-2) + calSteps(n-3);
    }
}
