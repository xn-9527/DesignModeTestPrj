package cn.soulutions.robotPositions;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，
 * 每一次只能向左，右，上，下四个方向移动一格，
 * 但是不能进入行坐标和列坐标的数位之和大于k的格子。
 * 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。
 * 但是，它不能进入方格（35,38），因为3+5+3+8 = 19。
 * 请问该机器人能够达到多少个格子？
 *
 * 前提：机器人可以重复行走走过的格子
 *
 * 运行时间：68ms
 * 超过0.39% 用Java提交的代码
 * 占用内存：21280KB
 * 超过0.17%用Java提交的代码
 *
 * Created by xiaoni on 2020/3/25.
 */
public class RobotPositionSolution2 {
    /**
     * 需要修改能重复行走的情况
     *
     * @param threshold
     * @param rows
     * @param cols
     * @return
     */
    public int movingCount(int threshold, int rows, int cols) {

        //打印迷宫，观察现象，重复可走
        /**
         * 比如 4, 12, 12的输出,1 代表可走，0代表不可走
         * [1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0]
         * [1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0]
         * [1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         * [1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         * [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         * [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         * [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         * [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         * [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         * [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         * [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         * [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         *
         */
        int count = 0;
        //外循环始终取边长小的
        int xMax = Math.min(rows, cols);
        //内循环始终取边长大的
        int yMax = Math.max(rows, cols);
        int[][] maze = new int[xMax][yMax];
        //上一次的x行，y前进的步数，如果上一行x的y步进是0，说明后面的x不可达了。
        int lastXRowY = -1;
        //第一次循环，从左往右，从上往下
        for (int x = 0; x < xMax; x++) {
            StringBuilder sb = new StringBuilder();
            if (lastXRowY == 0) {
                maze[x][0] = 0;
                sb.append(0).append(",");
                System.out.println(sb.toString());
                break;
            }
            for (int y = 0; y < yMax; y ++) {
                //延y轴连续可达;或继续往前遍历，如果从上面是可达，则这个点符合的也是可达
                boolean isMatchRule = isMatchRule(threshold, x, y);
                boolean startPointMatch = x == 0 && y ==0;
                boolean leftContinueMatch = y > 0 && maze[x][y-1]==1;
                boolean upContinueMatch = x > 0 && maze[x-1][y] == 1;
                if (isMatchRule && (startPointMatch || leftContinueMatch || upContinueMatch)) {
                    maze[x][y] = 1;
                    sb.append(1).append(",");
                    count ++;
                    lastXRowY = y;
                } else {
                    maze[x][y] = 0;
                    sb.append(0).append(",");
                    if (xMax == 1) {
                        //如果只有一行，后面的节点不可达
                        break;
                    }
                }
            }
            System.out.println(sb.toString());
        }
        //第二次循环，从右下角往左上角循环，碰到是1的跳过，是0的计算右边和下边是否连续可达
        for (int x = xMax-1; x >=0; x--) {
            for(int y = yMax - 1; y >=0; y--) {
                count = compensateByAroundPoint(threshold, count, xMax, yMax, maze, x, y);
            }
        }
        //第三次循环，从右上角往左下角
        for (int x = 0; x < xMax; x++) {
            for(int y = yMax - 1; y >=0; y--) {
                count = compensateByAroundPoint(threshold, count, xMax, yMax, maze, x, y);
            }
        }
        //第4次循环，从左下往右上角
        for (int x = xMax-1; x >=0; x--) {
            for(int y = 0; y < yMax; y++) {
                count = compensateByAroundPoint(threshold, count, xMax, yMax, maze, x, y);
            }
        }

        System.out.println("#########################");
        System.out.println(count);
        for (int i=0; i < xMax; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }
        return count;
    }

    /**
     * 补偿判断(x,y)是否符合要求，且四周可达
     *
     * @param threshold
     * @param count
     * @param xMax
     * @param yMax
     * @param maze
     * @param x
     * @param y
     * @return
     */
    private int compensateByAroundPoint(int threshold, int count, int xMax, int yMax, int[][] maze, int x, int y) {
        if (maze[x][y] == 1) {
            return count;
        }
        boolean isMatchRule = isMatchRule(threshold, x, y);
        boolean rightContinueMatch = y < (yMax - 1) && maze[x][y + 1] == 1;
        boolean downContinueMatch = x < (xMax - 1) && maze[x + 1][y] == 1;
        boolean leftContinueMatch = y > 0 && maze[x][y - 1] == 1;
        boolean upContinueMatch = x > 0 && maze[x - 1][y] == 1;
        if (isMatchRule && (leftContinueMatch || upContinueMatch || rightContinueMatch || downContinueMatch)) {
            maze[x][y] = 1;
            count++;
        }
        return count;
    }

    /**
     * 是否符合判断条件:不能进入行坐标和列坐标的数位之和大于k的格子
     *
     * @param x
     * @param y
     * @param threshold
     * @return
     */
    private boolean isMatchRule(int threshold, int x, int y) {
        return (sumBitNumber(x) + sumBitNumber(y)) <= threshold;
    }

    private int sumBitNumber(int x) {
        char[] chars = String.valueOf(x).toCharArray();
        int sum = 0;
        for (char c : chars) {
            sum += Integer.parseInt(String.valueOf(c));
        }
//        System.out.println("input:"+ x + ", bitSum:"+sum);
        return sum;
    }

    public static void main(String[] args) {
//        new RobotPositionSolution2().movingCount(1, 2, 3);
//        new RobotPositionSolution2().movingCount(10, 1, 100);
//        new RobotPositionSolution2().movingCount(15, 100, 1);
//        new RobotPositionSolution2().movingCount(4, 12, 12);
//        new RobotPositionSolution2().movingCount(15, 20, 20);
        new RobotPositionSolution2().movingCount(15, 100, 100);
    }
}
