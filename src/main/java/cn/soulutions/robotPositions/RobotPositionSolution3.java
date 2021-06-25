package cn.soulutions.robotPositions;

/**
 * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，
 *  每一次只能向左，右，上，下四个方向移动一格，
 *  但是不能进入行坐标和列坐标的数位之和大于k的格子。
 *  例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。
 *  但是，它不能进入方格（35,38），因为3+5+3+8 = 19。
 *  请问该机器人能够达到多少个格子？
 *
 *  前提：机器人可以重复行走走过的格子
 * 机器人的运动范围_牛客题霸_牛客网  https://www.nowcoder.com/practice/6e5207314b5241fb83f2329e89fdecc8?tpId=13&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking
 *
 * 运行时间：22ms
 * 超过16.81% 用Java提交的代码
 * 占用内存：11744KB
 * 超过0.89%用Java提交的代码
 *
 * @author Chay
 * @date 2021/6/24 21:30
 */
public class RobotPositionSolution3 {
    /**
     * 递归思想：上下左右遍历找合适的目标点，用数组存储这个点是否被标记过
     */
    public int movingCount(int threshold, int rows, int cols) {
        if (rows <= 0 || cols <= 0 || threshold < 0) {
            return 0;
        }
        boolean[][] isVisited = new boolean[rows][cols];
        int count = search(threshold, rows, cols, 0, 0, isVisited);
        return count;
    }

    private int search(int threshold, int rows, int cols, int row, int col, boolean[][] isVisited) {
        if (row < 0 || col < 0 || row >= rows || col >= cols
                /** 被计算过+1的不再加1 **/
                || isVisited[row][col]
                || cal(row) + cal(col) > threshold) {
            return 0;
        }
        isVisited[row][col] = true;
        return 1 + search(threshold, rows, cols, row - 1, col, isVisited)
                + search(threshold, rows, cols, row + 1, col, isVisited)
                + search(threshold, rows, cols, row, col + 1, isVisited)
                + search(threshold, rows, cols, row, col - 1, isVisited);
    }

    private int cal(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    public static void main(String[] args) {
//        System.out.println(new RobotPositionSolution3().movingCount(1, 2, 3));
//        System.out.println(new RobotPositionSolution3().movingCount(10, 1, 100));
//        System.out.println(new RobotPositionSolution3().movingCount(15, 100, 1));
//        System.out.println(new RobotPositionSolution3().movingCount(4, 12, 12));
//        System.out.println(new RobotPositionSolution3().movingCount(15, 20, 20));
//        System.out.println(new RobotPositionSolution3().movingCount(15, 100, 100));
        System.out.println(new RobotPositionSolution3().movingCount(0, 1, 3));
    }
}
