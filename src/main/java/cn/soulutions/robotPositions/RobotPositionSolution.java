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
 * 这里的解法，前提：机器人不能重复行走走过的格子
 * Created by xiaoni on 2020/3/25.
 */
@Deprecated
public class RobotPositionSolution {
    /**
     * 类似于A*算法
     * <p>
     * 事实证明A*算法并不太合适，因为题目条件是允许重复行走。
     *
     * 我用A*实现了不能重复行走的情况
     *
     * @param threshold
     * @param rows
     * @param cols
     * @return
     */
    public int movingCount(int threshold, int rows, int cols) {
        int count = 0;
        Grid endGrid = null;
        //可到达的格子
        Set<Grid> openList = new TreeSet<>();
        //未到达的格子
        Set<Grid> closeList = new TreeSet<>();

        //把起点加入
        openList.add(new Grid(0, 0));

        while (openList.size() > 0) {
            //在openList中查找总步数最长的格子，将其作为当前方格节点
            //问题在这里，当前最大值的格子，有可能不是全局最大值
            Grid currentGrid = findMaxCountGrid(openList, threshold);
            //目前找到的最大值
            if (currentGrid.g > count) {
                count = currentGrid.g;
                endGrid = currentGrid;
            }
            //将当前方格节点从openList中移除
            openList.remove(currentGrid);
            //当前方格节点进入closeList
            closeList.add(currentGrid);
            //找到所有的可走满足条件的邻近节点
            List<Grid> neighbors = findNeighbors(currentGrid, openList, closeList, threshold, rows - 1, cols - 1);
            for (Grid grid : neighbors) {
                if (!openList.contains(grid)) {
                    //如果邻近节点不在openList，初始化g，并放入openList
                    grid.initGrid(currentGrid);
                    openList.add(grid);
                }
            }
        }
        System.out.println(count);
        System.out.println(JSON.toJSONString(endGrid));
        return count + 1;
    }

    /**
     * 查找可到达的邻居节点
     *  @param currentGrid
     * @param openList
     * @param closeList
     * @param threshold
     * @param rowsLength
     * @param colsLength        @return
     */
    private List<Grid> findNeighbors(Grid currentGrid, Set<Grid> openList, Set<Grid> closeList, int threshold, int rowsLength, int colsLength) {
        List<Grid> gridList = new ArrayList<>();
        //校验当前节点下面的节点
        if (isValidGrid(currentGrid.x, currentGrid.y - 1, openList, closeList, threshold, rowsLength, colsLength)) {
            gridList.add(new Grid(currentGrid.x, currentGrid.y - 1));
        }
        //校验当前节点上面的节点
        if (isValidGrid(currentGrid.x, currentGrid.y + 1, openList, closeList, threshold, rowsLength, colsLength)) {
            gridList.add(new Grid(currentGrid.x, currentGrid.y + 1));
        }
        //校验当前节点左面的节点
//        if (isValidGrid(currentGrid.x - 1, currentGrid.y, openList, closeList, threshold, rows, cols)) {
//            gridList.add(new Grid(currentGrid.x - 1, currentGrid.y));
//        }
        //校验当前节点右面的节点
        if (isValidGrid(currentGrid.x + 1, currentGrid.y, openList, closeList, threshold, rowsLength, colsLength)) {
            gridList.add(new Grid(currentGrid.x + 1, currentGrid.y));
        }
        return gridList;
    }

    /**
     * 校验节点是不是可达
     *
     * @param x
     * @param y
     * @param openList
     * @param closeList
     * @param threshold
     * @param rowsLength
     * @param colsLength
     * @return
     */
    private boolean isValidGrid(int x, int y, Set<Grid> openList, Set<Grid> closeList, int threshold, int rowsLength, int colsLength) {
        //校验边界
        if (x < 0 || x >= rowsLength || y < 0 || y >= colsLength) {
            return false;
        }
        //是否符合判断条件:不能进入行坐标和列坐标的数位之和大于k的格子
        if (!isMatchRule(threshold, x, y)) {
            return false;
        }
        //校验是否已经在openList中
        if (containGrid(openList, x, y)) {
            //移除旧的点
            openList.remove(new Grid(x,y));
            //返回true，以便重新添加该点，并更新该点的g
            return true;
        }
        //校验是否已经在closeList中
        if (containGrid(closeList, x, y)) {
            return false;
        }

        return true;
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

    private boolean containGrid(Set<Grid> gridList, int x, int y) {
        for (Grid grid : gridList) {
            if (grid.x == x && grid.y == y) {
                return true;
            }
        }
        return false;
    }


    /**
     * 找到总步数最大的节点,且增长最小
     *
     * @param openList
     * @param threshold
     * @return
     */
    private Grid findMaxCountGrid(Set<Grid> openList, int threshold) {
        if (openList == null || openList.isEmpty()) {
            return null;
        }
        Grid maxCountGrid = null;
        int maxG = -1;
        int lastX = -1;
        for (Grid grid : openList) {
            boolean isBetter = grid.g > maxG
                    //如果g相等，那么优先取x不增加的,且y没有到达边界，
                    // 因为边界是死点没有可用的邻点
                    || (grid.g == maxG && grid.x < lastX && (grid.x + grid.y) < threshold);
            if (isBetter) {
                maxG = grid.g;
                maxCountGrid = grid;
                lastX = grid.x;
            }
        }
        return maxCountGrid;
    }

    public static class Grid implements Comparable {
        public int x;
        public int y;
        //从起点到当前格子的总步数
        public int g;
        public Grid parent;

        public Grid(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void initGrid(Grid parent) {
            this.parent = parent;
            if (parent != null) {
                this.g = parent.g + 1;
            } else {
                this.g = 0;
            }
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Grid)) {
                return false;
            }
            Grid grid = (Grid) o;
            if (grid.x == this.x && grid.y == this.y) {
                return true;
            }
            return false;
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Grid)) {
                return -1;
            }
            Grid grid = (Grid) o;
            if (grid.x > this.x) {
                return 1;
            } else if (grid.x < this.x) {
                return -1;
            } else if (grid.x == this.x) {
                if (grid.y > this.y) {
                    return 1;
                }
                if (grid.y == this.y) {
                    return 0;
                }
                return -1;
            }
            return -1;
        }
    }

    public static void main(String[] args) {
//        new RobotPositionSolution().movingCount(4, 5, 4);
//        new RobotPositionSolution().movingCount(2, 5, 4);
        new RobotPositionSolution().movingCount(5, 10, 10);
//        System.out.println(new RobotPositionSolution().sumBitNumber(4));
    }
}
