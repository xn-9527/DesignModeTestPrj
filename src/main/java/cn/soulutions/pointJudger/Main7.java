package cn.soulutions.pointJudger;

import java.util.*;


/**
 * @Author: xiaoni
 * @Date: 2020/3/16 19:12
 */
public class Main7 {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入总的点个数：");
        try {
            int pointCount = sc.nextInt();
            TreeSet<Point> pointList = new TreeSet<Point>();
            for (int i = 0; i < pointCount; i++) {
                pointList.add(new Point(sc.nextInt(), sc.nextInt()));
            }
            sc.close();

            findMaxPoint(pointList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        System.out.println(System.currentTimeMillis() - start);
    }

    public static class Point implements Comparable {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        //按x的倒序排
        @Override
        public int compareTo(Object o) {
            if (o instanceof Point) {
                Point op = (Point)o;
                if (op.x < this.x) {
                    //x小的，排在后面
                    return -1;
                } else if (op.x == this.x) {
                    if (op.y == this.y) {
                        //相等的，不变顺序
                        return 0;
                    } else if (op.y > this.y) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    //x只要大的，y无论大小，都优先认为大
                    return 1;
                }
            }
            return -1;
        }
    }

    /**
     * 70.00%
     * @param pointList
     */
    public static void findMaxPoint(TreeSet<Point> pointList) {
        int yMax = -1;
        Stack<Point> result = new Stack<>();
        for (Point p : pointList) {
            if (p.y > yMax) {
                yMax = p.y;
                result.push(p);
            }
        }
        while (!result.isEmpty()) {
            Point p = result.pop();
            System.out.println(p.x + " " + p.y);
        }
    }

}
