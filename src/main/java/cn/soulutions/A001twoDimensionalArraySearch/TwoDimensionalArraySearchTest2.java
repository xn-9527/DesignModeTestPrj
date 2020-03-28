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
public class TwoDimensionalArraySearchTest2 {

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
        TwoDimensionalArraySearchTest2 twoDimension = new TwoDimensionalArraySearchTest2();
        System.out.println(twoDimension.Find(20, a));
        System.out.println(twoDimension.Find(21, a));
        int[][] b = {{}};
        System.out.println(twoDimension.Find(21, b));
    }

    /**
     * 链接：https://www.nowcoder.com/questionTerminal/abc3fe2ce8e146608e868a70efebf62e?toCommentId=5655595
     来源：牛客网

     最佳答案：没有之一。
     思路：首先我们选择从左下角开始搜寻，
     (为什么不从左上角开始搜寻，左上角向右和向下都是递增，那么对于一个点，
     对于向右和向下会产生一个岔路；如果我们选择从左下脚开始搜寻的话，
     如果大于就向右，如果小于就向下)。
     *
     * 左下角开始，遇大右移，遇小上移，
     * 直到超过边界都没找到，得false。否则得true。
     *
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int [][] array) {
//        链接：https://www.nowcoder.com/questionTerminal/abc3fe2ce8e146608e868a70efebf62e?toCommentId=5655595
        int len = array.length-1;
        int i = 0;
        while((len >= 0)&& (i < array[0].length)){
            if(array[len][i] > target){
                len--;
            }else if(array[len][i] < target){
                i++;
            }else{
                return true;
            }
        }
        return false;
    }
}
