package cn.soulutions.findMostLongEqualDiffArray;

import java.util.*;

/**
 * 给定一个未排序数组,找出其中最长的等差数列(无需保证数字顺序)。
 *
 * 输入描述:
 * 第一行N表示数组中元素个数（N < 10,000,000）
 *
 * 第二行是数组的元素，用空格分割
 *
 * 输出描述:
 * 等差序列长度
 *
 * 输入例子1:
 * 5
 * 1 4 2 5 3
 *
 * 输出例子1:
 * 5
 *
 * @author Chay
 * @date 2020/3/20 18:16
 */
public class FindMostLongEqualDiffArray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer> input = new ArrayList<>(n);
        for (int i = 0; i< n;i++) {
            input.add(sc.nextInt());
        }

        solution(input, n);
    }

    /**
     * 先排序，理解成x轴坐标
     * 然后依次计算差值，并往后加差值的n倍，直到大于最后一个数停止，
     * 如果循环过程中，发现数组中有该值，则统计结果+1
     *
     * 记录上次遍历结果比较，输出最长的结果
     *
     * 通过率：100.00%
     *
     * @param input
     * @param n
     */
    private static void solution(ArrayList<Integer> input, int n) {
        int size = input.size();
        if (size <= 2) {
            System.out.println("1");
            return;
        }

//        ArrayList<Integer> thisResult = new ArrayList<>();
//        ArrayList<Integer> lastResult = new ArrayList<>();
        int thisResultCount = 0;
        int lastResultCount = 0;
        input.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        for (int i = 0; i < size - 1; i++) {
            int ni = input.get(i);
            for (int j = i + 1; j < size; j ++) {
                int nj = input.get(j);
                int d = nj - ni;

                //添加本次结果的前两个个数
                thisResultCount = 2;
//                thisResult.clear();
//                thisResult.add(ni);
//                thisResult.add(nj);
                int lastK = j;
                for (int k = j + 1; k < size; k++) {
                    int dk = input.get(k) - input.get(lastK);
                    if (dk == d) {
//                        thisResult.add(nk);
                        thisResultCount ++;
                        lastK = k;
                    } else if (dk > d) {
                        //差值大于才能跳出，差值小于的话k继续++往下循环
                        break;
                    }
                }
                //循环完毕，如果本次循环结果大于上次缓存结果，则替换上次缓存结果
//                if (thisResult.size() > lastResult.size()) {
//                    lastResult.clear();
//                    lastResult.addAll(thisResult);
//                }
                if (thisResultCount > lastResultCount) {
                    lastResultCount = thisResultCount;
                }
            }
        }
//        System.out.println(lastResult);
        System.out.println(lastResultCount);
    }
}
