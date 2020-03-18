package cn.soulutions.getLeastNumber;

import java.util.ArrayList;

/**
 * @author Chay
 * @date 2020/3/18 22:07
 */
public class GetLeastNumberSolution {
    public static ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        if (k > input.length) {
            return new ArrayList<>(1);
        }
        quickSort(input, 0, input.length - 1);
        ArrayList<Integer> result = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            result.add(input[i]);
        }
        return result;
    }

    public static void quickSort(int[] input, int start, int end) {
        if (start >= end) {return;}
        int middle = getMiddle(input, start, end);
        quickSort(input, start, middle -1);
        quickSort(input, middle + 1, end);
    }

    public static int getMiddle(int[] input, int start, int end) {
        int pivot = input[start];
        int left = start;
        int right = end;
        while (left != right) {
            while (left < right && input[right] > pivot) {
                right--;
            }
            while (left < right && input[left] <= pivot) {
                left++;
            }
            if (left < right) {
                swap(input, left, right);
            }
        }
        input[start] = input[left];
        input[left] = pivot;

        return left;
    }

    public static void swap(int[] input, int a, int b) {
        int temp = input[a];
        input[a] = input[b];
        input[b] = temp;
    }

    public static void main(String[] args) {
        int[] input = {1, 3, 6,2,8, 20, 15};
        System.out.println(GetLeastNumbers_Solution(input, 4));
    }
}
