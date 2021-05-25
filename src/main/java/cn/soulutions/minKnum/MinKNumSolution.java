package cn.soulutions.minKnum;

import java.util.*;

/**
 * 给定一个数组，找出其中最小的K个数。例如数组元素是4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。如果K>数组的长度，那么返回一个空的数组
 *
 * @author Chay
 * @date 2021/5/25 9:48
 */
public class MinKNumSolution {
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> solution = new ArrayList<>(k);
        if (k > input.length) {
            return solution;
        }
        //排序
        quickSort(input, 0, input.length - 1);
        System.out.println(Arrays.toString(input));
        //初始化solution
        for (int i = 0; i < k; i++) {
            solution.add(input[i]);
        }
        return solution;
    }

    private void quickSort(int[] input, int start, int end) {
        if (start >= end) {
            return;
        }
        int middle = getMiddle(input, start, end);
        quickSort(input, start, middle - 1);
        quickSort(input, middle + 1, end);
    }

    private int getMiddle(int[] input, int start, int end) {
        int middle = input[start];
        int left = start;
        int right = end;
        while (left != right) {
            //先右
            while (left < right && input[right] > middle) {
                right--;
            }
            //再左
            while (left < right && input[left] <= middle) {
                left++;
            }

            //交换比中位数大的左和比中位数小的右
            if (left < right) {
                int temp = input[left];
                input[left] = input[right];
                input[right] = temp;
            }
        }
        //交换中位数和比中位数小的左
        input[start] = input[left];
        input[left] = middle;
        return left;
    }

    public static void main(String[] args) {
        MinKNumSolution minKNumSolution = new MinKNumSolution();
        System.out.println(minKNumSolution.GetLeastNumbers_Solution(new int[]{4,5,1,6,2,7,3,8}, 4));
    }
}
