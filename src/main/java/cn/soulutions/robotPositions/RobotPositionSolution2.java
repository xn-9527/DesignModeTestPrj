package cn.soulutions.robotPositions;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，
 * 每一次只能向左，右，上，下四个方向移动一格，
 * 但是不能进入行坐标和列坐标的数位之和大于k的格子。
 * 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。
 * 但是，它不能进入方格（35,38），因为3+5+3+8 = 19。
 * 请问该机器人能够达到多少个格子？
 *
 * 前提：机器人可以重复行走走过的格子
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
         * 比如 5,10,10的输出
         *   0,0,0,0,0,0,1,1,1,1,
             0,0,0,0,0,1,1,1,1,1,
             0,0,0,0,1,1,1,1,1,1,
             0,0,0,1,1,1,1,1,1,1,
             0,0,1,1,1,1,1,1,1,1,
             0,1,1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,1,1,
         *
         */
        int count = 0;
        //外循环始终取小的
        int xMax = Math.min(rows, cols);
        //内循环始终取大的
        int yMax = Math.max(rows, cols);
        int[][] maze = new int[xMax][yMax];
        for (int x = 0; x < xMax; x++) {
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < yMax; y ++) {
                if (isMatchRule(threshold, x, y)) {
                    maze[x][y] = 0;
                    sb.append(0).append(",");
                    count ++;
                } else {
                    maze[x][y] = 1;
                    sb.append(1).append(",");
                    if (xMax == 1) {
                        //如果只有一行，后面的节点不可达
                        break;
                    }
                }
            }
//            System.out.println(sb.toString());
        }

        System.out.println(count);
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
        return sum;
    }

    public static void main(String[] args) {
//        new RobotPositionSolution2().movingCount(10, 1, 100);
        new RobotPositionSolution2().movingCount(15, 100, 1);
    }
}
