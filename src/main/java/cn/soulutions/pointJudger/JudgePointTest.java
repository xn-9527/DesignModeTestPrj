package cn.soulutions.pointJudger;

import java.util.*;

/**
 * @Author: xiaoni
 * @Date: 2020/3/16 19:12
 */
public class JudgePointTest {
    public static class Point {
        private int x;
        private int y;
        private final double max = Math.expm1(9);
        private final static int min = 0;

        public Point(int x, int y) {
            validAxe(x);
            validAxe(y);
            this.x = x;
            this.y = y;
        }

        void validAxe(int number) {
            if (number < min || number >= max) {
                throw new IllegalArgumentException("x或y的坐标范围是[0,1e9),请重新输入");
            }
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static void validPointCount(Integer pointCount) {
        if (!(pointCount instanceof Integer) || pointCount <= 0) {
            throw new IllegalArgumentException("请输入正确的点个数！");
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

                for (int j = i; j< size; j++) {
                    if (loosers.contains(j)) {
                        continue;
                    }
                    Point nextP = pointList.get(j);
                    int nextX = nextP.getX();
                    int nextY = nextP.getY();

                    if (thisX <= nextX && thisY <= nextY) {
                        loosers.add(i);
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入总的点个数：");
        try {
            int pointCount = sc.nextInt();
            JudgePointTest.validPointCount(pointCount);
            List<Point> pointList = new ArrayList<>(pointCount);
            for (int i = 0; i < pointCount; i++) {
                System.out.println("请输入第" + (i + 1) + "个数");
                String nums = null;
                while (nums == null || "".equals(nums))
                {
                    if (System.in.available() > 0)
                    {
                        nums = sc.nextLine();
//                        System.out.println(nums);
                    }
                }
                String[] splitNums = nums.split(" ");
                if (splitNums.length != 2) {
                    throw new IllegalArgumentException("请输入正确的x y坐标");
                }
                pointList.add(new Point(Integer.parseInt(splitNums[0]), Integer.parseInt(splitNums[1])));
            }

            JudgePointTest.findMaxPoint(pointList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
