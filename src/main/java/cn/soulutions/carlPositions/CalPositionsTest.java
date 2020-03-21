package cn.soulutions.carlPositions;

import java.util.Scanner;

/**
 * 给定N（可选作为埋伏点的建筑物数）、D（相距最远的两名特工间的距离的最大值）以及可选建筑的坐标，计算在这次行动中，大锤的小队有多少种埋伏选择。
 * 注意：
 * 1. 两个特工不能埋伏在同一地点
 * 2. 三个特工是等价的：即同样的位置组合(A, B, C) 只算一种埋伏方法，不能因“特工之间互换位置”而重复使用
 * <p>
 * <p>
 * 输入描述:
 * 第一行包含空格分隔的两个数字 N和D(1 ≤ N ≤ 1000000; 1 ≤ D ≤ 1000000)
 * <p>
 * 第二行包含N个建筑物的的位置，每个位置用一个整数（取值区间为[0, 1000000]）表示，从小到大排列（将字节跳动大街看做一条数轴）
 * <p>
 * 输出描述:
 * 一个数字，表示不同埋伏方案的数量。结果可能溢出，请对 99997867 取模
 * <p>
 * 输入例子1:
 * 4 3
 * 1 2 3 4
 * <p>
 * 输出例子1:
 * 4
 * <p>
 * 例子说明1:
 * 可选方案 (1, 2, 3), (1, 2, 4), (1, 3, 4), (2, 3, 4)
 *
 * @author Chay
 * @date 2020/3/20 15:42
 */
public class CalPositionsTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int d = sc.nextInt();

        //存放当前坐标，和距离上一坐标的差值
        int[] positions = new int[n];
        for (int i = 0; i < n; i++) {
            positions[i] = sc.nextInt();
        }
        sc.close();

//        solution(positions, n, d);
//        solution2(positions, n, d);
        System.out.println(solution3(positions, d));
    }

    /**
     * 三层循环运行超时，通过率20%
     *  样本多的时候，卡死
     *
     * @param positions
     * @param n
     * @param d
     */
    public static void solution(int[] positions, int n, int d) {
        //因为部署特工的距离要求小于d，所以当累计距离大于d时，就得向后平移，进行新的判断,取所有3个连续的建筑物坐标
        int count = 0;

        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        int d2 = 0;
        int d3 = 0;
        for (int i = 0; i < n - 2; i++) {
            p1 = positions[i];
            innerLoop:
            for (int j = i + 1; j < n - 1; j++) {
                p2 = positions[j];
                d2 = p2-p1;
                if (d2 > d) {
                    break innerLoop;
                }
                for (int k = j + 1; k < n; k++) {
                    p3 = positions[k];
                    d3 = p3 - p1;
                    if (d3 > d) {
                        break innerLoop;
                    }
                    //System.out.println(positions[i] + "," + positions[j] + "," + positions[k]);
                    count ++;
                }
            }
        }
        System.out.println(count);
    }

    /***
     * 样本多的时候，卡死，通过率20%
     *
     * @param positions
     * @param n
     * @param d
     */
    public static void solution2(int[] positions, int n, int d) {
        //因为部署特工的距离要求小于d，所以当累计距离大于d时，就得向后平移，进行新的判断,取所有3个连续的建筑物坐标
        int count = 0;

        int p1 = 0;
        int p3 = 0;
        int d3 = 0;
        for (int i = 0; i < n - 2; i++) {
            p1 = positions[i];
            innerLoop:

            for (int k = i + 2; k < n; k++) {
                p3 = positions[k];
                d3 = p3 - p1;
                if (d3 > d) {
                    break innerLoop;
                }
                //System.out.println(positions[i] + "," + positions[j] + "," + positions[k]);
                //首尾特工位置i和k已经确定，所以第二个特工的位置就是从i+1到k-1, 差值就是k-i-2，然后对应差值 + 1种情况
                count = count + k - i - 1;
            }
        }
        System.out.println(count);
    }
    public final static long MOD = 99997867;

    /**
     * end先行，然后往前找最远的start，因为end的位置已经确定，所以只需要前面2个从最远start位置开始到end位置Cn取2就可以了
     *
     * @param positions
     * @param d
     * @return
     */
    public static long solution3(int[] positions, int d) {
        long count = 0;
        for (int end = 0,start = 0;end<positions.length;end++){
            while (end >= 2 && (positions[end] - positions[start]) > d) {
                start++;
            }
            count += getNFromC(end - start);
        }
        return count % MOD;
    }

    /**
     * 作者：jsnsacksajsbjd
     * 链接：https://www.nowcoder.com/questionTerminal/c0803540c94848baac03096745b55b9b
     * 来源：牛客网
     *
     * 就比如，，
     * 6 4
     * 1 2 3 4 5 6
     * 选1.然后2 3 4 5中选2个
     * 选2，然后3 4 5 6选2个，，，这就是题目的计算过程，但是实际1 4 5算了两次，但是3 4 5这种选择没有算上，，，，相抵消了个数，
     *
     *
     * @param i
     * @return
     */
    private static long getNFromC(long i) {
        return i * (i - 1)/2;
    }
}
