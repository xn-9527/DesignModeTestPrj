package cn.soulutions.longestIncreasingSubsequence;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author Chay
 * @date 2020/3/21 22:37
 */
public class LISTest {
    public static void main(String[] args) {
        int[] input = {1, 3, 7, 4, 6, 5, 9, 2,8,20,15,13,14,16,12};
        //0存值，1存总序列长度，2存前一个元素下标
        int[][] sequence = new int[input.length][3];
        for (int i = 0; i < input.length; i++) {
            sequence[i][0] = input[i];
            sequence[i][1] = 1;
            sequence[i][2] = DEFAULT_LAST_INDEX;
        }
        solution1(sequence);
        solution2(input);
    }
    public final static int DEFAULT_LAST_INDEX = -1;

    /**
     * n ^ 2 解法（双层循环递推关系式）
     *
     * @param input
     */
    public static void solution1(int[][] input) {
        //用来记录最长序列长度
        int maxx = 0;
        //pos用来记录最长递增子序列的末尾数字下表
        int pos = -1;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < i; j++) {
                //0存值，1存总序列长度，2存前一个元素下标
                //j的值小于i的值，且j的深度+1>i的深度
                if (input[j][0] < input[i][0] && input[j][1] + 1 > input[i][1]) {
                    input[i][1] = input[j][1] + 1;
                    input[i][2] = j;
                    if (maxx < input[i][1]) {
                        maxx = input[i][1];
                        pos = i;//更新pos
                    }
                }
            }
        }
        System.out.println("maxx:" + maxx + ", longest increasing sub sequence last pos:" + pos);
        //如果要输出最长序列的长度，输出maxx即可。
        //如果要输出最长序列的结果，则从pos对应的元素开始，往前遍历上一个元素的下标，最后正序输出即可
        Stack<Integer> stack = new Stack<Integer>();
        int lastIndex = pos;
        while (lastIndex != DEFAULT_LAST_INDEX) {
            stack.push(input[lastIndex][0]);
            lastIndex = input[lastIndex][2];
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

        for (int i = 0; i < input.length; i++) {
            System.out.println("原值：" + input[i][0] + ",深度" + input[i][1] + ",上一个元素下标" + input[i][2]);
        }

    }

    private final static int INF = 0x7fffffff;
    /**
     * n * logn解法（贪心思想 二分优化）
     * 将原来的dp数组的存储由数值换成该序列中，上升子序列长度为i的上升子序列，的最小末尾数值
     * 这其实就是一种几近贪心的思想：我们当前的上升子序列长度如果已经确定，那么如果这种长度的子序列的结尾元素越小，
     * 后面的元素就可以更方便地加入到这条我们臆测的、可作为结果、的上升子序列中。
     *
     * 缺点：无法详细记录具体路径，虽然时间复杂度降低了
     *
     * 时间复杂度越高的算法越全能
     * <p>
     * Junior Dynamic Programming——动态规划初步·各种子序列问题 - 笨蛋花的小窝qwq - 洛谷博客
     * https://www.luogu.com.cn/blog/pks-LOVING/junior-dynamic-programming-dong-tai-gui-hua-chu-bu-ge-zhong-zi-xu-lie
     *
     * @param input
     */
    public static void solution2(int[] input) {
        int n = input.length;
        int[] f = new int[n];
        for (int i = 0; i < n; i++) {
            //初始值要设为INF
            /*原因很简单，每遇到一个新的元素时，就跟已经记录的f数组当前所记录的最长
             * 上升子序列的末尾元素相比较：如果小于此元素，那么就不断向前找，直到找到
             * 一个刚好比它大的元素，替换；反之如果大于，么填到末尾元素的下一个q，INF
             * 就是为了方便向后替换啊！*/
            f[i] = INF;
        }
        f[0] = input[0];
        //通过记录f数组的有效位数，求得个数
        int len = 1;
        /*因为上文中所提到我们有可能要不断向前寻找，
         * 所以可以采用二分查找的策略，这便是将时间复杂
         * 度降成nlogn级别的关键因素。*/
        for (int i = 1; i < n; i++) {
            int left = 0, right = len, mid;
            if (input[i] > f[len]) {
                //如果刚好大于末尾，暂时向后顺次填充
                f[++len] = input[i];
            } else {
                while (left < right) {
                    mid = (left + right) / 2;
                    if (f[mid] > input[i]) {
                        right = mid;
                        //如果仍然小于之前所记录的最小末尾，那么不断
                        //向前寻找(因为是最长上升子序列，所以f数组必
                        //然满足单调)
                    } else {
                        left = mid + 1;
                    }
                }
                //更新最小末尾
                f[left] = Math.min(input[i], f[left]);
            }
        }
        System.out.println(Arrays.toString(f));
    }
}
