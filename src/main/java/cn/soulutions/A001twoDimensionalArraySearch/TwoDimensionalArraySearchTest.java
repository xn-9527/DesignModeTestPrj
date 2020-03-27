package cn.soulutions.A001twoDimensionalArraySearch;

import java.util.Arrays;

/**
 * 在一个二维数组中（每个一维数组的长度相同），
 * 每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，
 * 判断数组中是否含有该整数。
 *
 * TIPS：不同行是数字可能有重复
 * Created by xiaoni on 2020/3/27.
 */
public class TwoDimensionalArraySearchTest {

    public static void main(String[] args) {
        int[][] a = {
                {1,2,4,6,9, 10},
                {2,20,31,42,51,52},
                {4,62,63,65,67,68},
                {8,73,74,76,77,78},
                {7,99,102,110,115,116}
        };

//        int[][] a = {{}};
        /**
         * @param from the initial index of the range to be copied, inclusive
         * @param to the final index of the range to be copied, exclusive.
         *     (This index may lie outside the array.)
         *  左闭右开
         */
//        int[][] b = Arrays.copyOfRange(a, 0, 2);
//        int[][] c = Arrays.copyOfRange(a, 2, 3);
        System.out.println(new TwoDimensionalArraySearchTest().Find(20, a));
    }

    /**
     * 采用二分查找的思想
     *
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int [][] array) {
        int xLength = array.length;
        int yLength = array[0].length;
        if (xLength > 1) {
            //先找到x，再对y二分查找
            int middleXIndex = xLength / 2;
            int middleXMin = array[middleXIndex][0];
            int middleXMax = array[middleXIndex][yLength - 1];
            if (middleXMin == target || middleXMax == target) {
                //相等
                return true;
            } else if (target < middleXMin) {
                //递归上半部分
                return Find(target, Arrays.copyOfRange(array, 0, middleXIndex));
            } else if (target > middleXMax) {
                //递归下半部分
                return Find(target, Arrays.copyOfRange(array, middleXIndex + 1, xLength));
            } else {
                //无法缩小范围，只能遍历
                for (int i = 0; i < xLength; i++) {
                    if (FindOneRow(target, array[i])) {
                        return true;
                    }
                }
                return false;
            }
        } else if (xLength == 1) {
            //只有一列
            return FindOneRow(target, array[0]);
        } else {
            //没有数据
            return false;
        }
    }

    public boolean FindOneRow(int target, int[] array) {
        int yLength = array.length;
        //只有一列
        if (yLength == 1) {
            //只有一个元素
            return array[0] == target;
        } else if (yLength > 1){
            //多余一个元素
            int middleYIndex = yLength / 2;
            int middleY = array[middleYIndex];
            if (middleY == target) {
                return true;
            } else if (target < middleY) {
                //递归左半边
                return FindOneRow(target, Arrays.copyOfRange(array, 0, middleYIndex));
            } else if (target > middleY) {
                //递归右半边
                return FindOneRow(target, Arrays.copyOfRange(array, middleYIndex + 1, yLength));
            } else {
                return false;
            }
        } else {
            //没有元素
            return false;
        }
    }
}
