package cn.soulutions.divideMinDiffSet;

import java.util.Scanner;

/**
 * 给定一个数组，每个元素范围是0~K（K < 整数最大值2^32），将该数组分成两部分，使得 |S1- S2|最小，其中S1和S2分别是数组两部分的元素之和。
 *
 *
 *
 * 输入描述:
 * 数组元素个数N（N 大于1但不超过 10, 000, 000）
 *
 * 数组中N个元素（用空格分割）
 *
 * 输出描述:
 * |S1- S2|的值
 *
 * 输入例子1:
 * 5
 * 2 4 5 6 9
 *
 * 输出例子1:
 * 0
 *
 * 输入例子2:
 * 4
 * 1 1 1 999
 *
 * 输出例子2:
 * 996
 *
 * @author Chay
 * @date 2020/3/20 18:02
 */
public class DivideMinDiffSetTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] input = new int[n];
        for (int i = 0; i< n;i++) {
            input[i] = sc.nextInt();
        }

        solution(input, n);
    }

    //两个集合间差值的绝对值最小
    private static void solution(int[] input, int n) {
        //先排序
        quickSort(input, 0, n-1);
        int diff = input[n - 1];
        //从大往小遍历,计算差值，正就减下一个数，负就加下一个数，始终保证在0附近
        for (int i = n - 2; i >= 0; i--) {
            if (diff >= 0) {
                diff -= input[i];
            } else {
                diff += input[i];
            }
        }
        System.out.println(Math.abs(diff));
    }

    public static void quickSort(int[] input, int start, int end) {
        if (start >= end) {
            return;
        }
        int middle = getMiddle(input, start, end);
        quickSort(input, start, middle - 1);
        quickSort(input, middle + 1, end);
    }

    public static int getMiddle(int[] input, int start, int end) {
        int pivot = input[start];
        int left = start;
        int right = end;
        while (left != right) {
            while(left < right && input[right] > pivot) {
                right --;
            }
            while(left < right && input[left] <= pivot) {
                left ++;
            }
            if (left < right) {
                swap(input, left, right);
            }
        }
        input[start] = input[left];
        input[left] = pivot;

        return left;
    }

    public static void swap(int[] input, int i, int j) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }
}
