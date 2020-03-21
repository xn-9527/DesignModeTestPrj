package cn.soulutions.pointJudger;

import java.util.*;

/**
 * @Author: xiaoni
 * @Date: 2020/3/16 19:12
 */
public class Main3 {

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

            Main3.findMaxPoint(pointList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void findMaxPoint(int[][] pointList) {
        int count = pointList.length;
        Set<Integer> loosers = new HashSet<>(count);
        if (count > 1) {
            for (int i=0 ;i < count - 1; i++) {
                if (loosers.contains(i)) {
                    continue;
                }
                int thisX = pointList[i][0];
                int thisY = pointList[i][1];

                inner:
                for (int j = i + 1; j< count; j++) {
                    if (loosers.contains(j)) {
                        continue;
                    }
                    int nextX = pointList[j][0];
                    int nextY = pointList[j][1];

                    if ((thisX <= nextX && thisY < nextY) || (thisX < nextX && thisY <= nextY)) {
                        loosers.add(i);
                        break inner;
                    } else if ((thisX >= nextX && thisY > nextY) || (thisX > nextX && thisY >= nextY)) {
                        loosers.add(j);
                    }
                }
            }
        }

        for (int i = 0; i < count; i++) {
            if (loosers.contains(i)) {
                continue;
            }
            System.out.println(pointList[i][0] + " " + pointList[i][1]);
        }
    }

}
