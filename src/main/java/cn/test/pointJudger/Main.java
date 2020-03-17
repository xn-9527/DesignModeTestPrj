package cn.test.pointJudger;

import java.util.*;

/**
 * @Author: xiaoni
 * @Date: 2020/3/16 19:12
 */
public class Main {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入总的点个数：");
        try {
            int pointCount = sc.nextInt();
            List<Point> pointList = new ArrayList<>(pointCount);
            for (int i = 0; i < pointCount; i++) {
                String nums = null;
                while (nums == null || "".equals(nums))
                {
                    if (System.in.available() > 0)
                    {
                        nums = sc.nextLine();
                    }
                }
                String[] splitNums = nums.split(" ");
                if (splitNums.length != 2) {
                    throw new IllegalArgumentException("请输入正确的x y坐标");
                }
                pointList.add(new Point(Integer.parseInt(splitNums[0]), Integer.parseInt(splitNums[1])));
            }
            sc.close();

            Main.findMaxPoint(pointList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static void findMaxPoint(List<Point> pointList) {
        if (pointList == null || pointList.isEmpty()) {
            return;
        }
        Set<Integer> loosers = new HashSet<>(pointList.size());
        int size = pointList.size();
        if (pointList.size() > 1) {
            for (int i=0 ;i < size - 1; i++) {
                if (loosers.contains(i)) {
                    continue;
                }
                Point thisP = pointList.get(i);
                int thisX = thisP.getX();
                int thisY = thisP.getY();

                inner:
                for (int j = i + 1; j< size; j++) {
                    if (loosers.contains(j)) {
                        continue;
                    }
                    Point nextP = pointList.get(j);
                    int nextX = nextP.getX();
                    int nextY = nextP.getY();

                    if (thisX <= nextX && thisY <= nextY) {
                        loosers.add(i);
                        break inner;
                    } else if (thisX >= nextX && thisY >= nextY) {
                        loosers.add(j);
                    }
                }
            }
        }

        for (int i = 0; i < size + 1; i++) {
            if (loosers.contains(i)) {
                continue;
            }
            Point point = pointList.get(i);
            System.out.println(point.getX() + " " + point.getY());
        }
    }

}
