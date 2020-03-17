package cn.test.pointJudger;

import java.util.*;

/**
 * @Author: xiaoni
 * @Date: 2020/3/16 19:12
 */
public class Main4 {

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

            Main4.findMaxPoint(pointList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void findMaxPoint(int[][] pointList) {
        int count = pointList.length;
        Set<Integer> loosers = new HashSet<>(count);
        //按x轴升序
        quickSort(pointList, 0, count - 1, loosers);

        //排序时排除部分后剩下的元素
        int remain = count - loosers.size();
        if (remain <= 1) {
            //只剩一个了，肯定就是最佳结果
            for (int i = 0; i< count; i++) {
                if (loosers.contains(pointList[i][0])) {
                    continue;
                }
                System.out.println(pointList[i][0] + " " + pointList[i][1]);
            }
        } else {
            //二次筛选
            int[][] remainList = new int[remain][2];
            int k = 0;
            for (int i = 0; i < count; i++) {
                if (loosers.contains(pointList[i][0])) {
                    continue;
                }
                remainList[k][0] = pointList[i][0];
                remainList[k][1] = pointList[i][1];
                k++;
            }

            //剩下的升序元素比较x前 < x后，所以如果y前 < y后，则i排除
            loosers.clear();
            for (int i = 0; i < remain - 1; i++) {
                inner:
                for (int j = i + 1; j < remain; j++) {
                    if (loosers.contains(remainList[i][0])) {
                        continue;
                    }
                    if (remainList[i][1] < remainList[j][1]) {
                        loosers.add(remainList[i][0]);
                        break inner;
                    }
                }
            }

            for (int i = 0; i < remain; i++) {
                if (loosers.contains(remainList[i][0])) {
                    continue;
                }
                System.out.println(remainList[i][0] + " " + remainList[i][1]);
            }
        }

    }

    /**
     * @param pointList
     * @param low
     * @param high
     * @param loosers   记录被淘汰的x坐标
     */
    private static void quickSort(int[][] pointList,
                                  int low,
                                  int high,
                                  Set loosers) {
        if (low < high) {
            //将list数组进行一分为二
            int middle = getMiddle(pointList, low, high, loosers);
            //对低字表进行递归排序
            quickSort(pointList, low, middle - 1, loosers);
            //对高字表进行递归排序
            quickSort(pointList, middle + 1, high, loosers);
        }
    }

    private static int getMiddle(int[][] pointList, int low, int high, Set loosers) {
        //数组的第一个作为基准数
        int[] temp = pointList[low];

        int thisY;
        int nextY = temp[1];
        while (low < high) {
            while (low < high && pointList[high][0] >= temp[0]) {
                thisY = pointList[high][1];
                //这是looser的判断
                if (loosers.contains(temp[0])) {
                    //已经包含了就不判断了
                } else if (thisY > nextY) {
                    //x>= netxtX & y > nextY, next loose
                    loosers.add(temp[0]);
                }

                high--;
            }

            //比中轴小的记录移到低端
            pointList[low] = pointList[high];
            while (low < high && pointList[low][0] <= temp[0]) {
                thisY = pointList[low][1];
                //这是looser的判断
                if (loosers.contains(pointList[low][0])) {
                    //已经包含了就不判断了
                } else if (thisY < nextY) {
                    //x<= netxtX & y < nextY, this loose
                    loosers.add(pointList[low][0]);
                }

                low++;
            }

            //比中轴大的记录移到高端
            pointList[high] = pointList[low];
        }
        //基准数记录到尾
        pointList[low] = temp;

        //返回基准数的位置
        return low;
    }

}
