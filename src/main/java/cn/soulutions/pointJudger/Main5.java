package cn.soulutions.pointJudger;

import java.util.*;


/**
 * @Author: xiaoni
 * @Date: 2020/3/16 19:12
 */
public class Main5 {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入总的点个数：");
        try {
            int pointCount = sc.nextInt();
            int[][] pointList = new int[pointCount][2];
            for (int i = 0; i < pointCount; i++) {
                pointList[i][0] = sc.nextInt();
                pointList[i][1] = sc.nextInt();
            }
            sc.close();

            findMaxPoint(pointList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 50%
     * @param pointList
     */
    public static void findMaxPoint(int[][] pointList) {
        int count = pointList.length;
        //按x轴升序
        quickSort(pointList, 0, count - 1);

        //比较x升序后的点集y，i从末尾开始，已知x[i] > x[i-1],跟最大的前面最大的yMax比较，
        // 若y[i] > yMax，则更新yMax=y[i]，并输出point[i]（因为该点肯定已经符合要求了，x最大，y也最大）
        // 如果y[i] < yMax,则该点不一定淘汰
        int yMax = -1;
        StringBuilder sb = new StringBuilder();
        for (int i = count - 1; i >= 0; i--) {
            if (pointList[i][1] > yMax) {
                yMax = pointList[i][1];
                //要x正序输出
                sb.insert(0, pointList[i][0] + " " + pointList[i][1] + "\n");
            }
        }
        System.out.print(sb.toString());
    }

    /**
     * @param pointList
     * @param low
     * @param high
     */
    private static void quickSort(int[][] pointList,
                                  int low,
                                  int high) {
        if (low < high) {
            //将list数组进行一分为二
            int middle = getMiddle(pointList, low, high);
            //对低字表进行递归排序
            quickSort(pointList, low, middle - 1);
            //对高字表进行递归排序
            quickSort(pointList, middle + 1, high);
        }
    }

    private static int getMiddle(int[][] pointList, int start, int end) {
        //数组的第一个作为基准数
        int[] temp = pointList[start];
        int low = start;
        int high = end;
        while (low < high) {
            while (low < high && pointList[high][0] >= temp[0]) {
                high--;
            }

            while (low < high && pointList[low][0] <= temp[0]) {
                low++;
            }
            if (low < high) {
                swap(pointList, low, high);
            }

        }
        //基准数记录到尾
        pointList[start] = pointList[low];
        pointList[low] = temp;

        //返回基准数的位置
        return low;
    }

    private static void swap(int[][] pointList, int low, int high) {
        int[] temp = pointList[high];
        pointList[high] = pointList[low];
        pointList[low] = temp;
    }

}
