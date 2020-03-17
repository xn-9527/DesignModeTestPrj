package cn.sort.quickSort;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author: xiaoni
 * @Date: 2020/3/17 23:38
 */
public class QuickSortTest {

    public static void quickSortDoubleRound(int[] input, int startIndex, int endIndex) {
        //递归结束时条件：startIndex 大于或等于 endIndex时
        if (startIndex >= endIndex) {
            return;
        }
        //得到基准元素的位置
        int pivotIndex = partition(input, startIndex, endIndex);
        //根据基准元素，分成两部分进行递归排序
        quickSortDoubleRound(input, startIndex, pivotIndex - 1);
        quickSortDoubleRound(input, pivotIndex + 1, endIndex);
    }

    /**
     * 分治(双边循环法)
     *
     * @param input
     * @param startIndex
     * @param endIndex
     * @return
     */
    private static int partition(int[] input, int startIndex, int endIndex) {
        //取第一个位置(也可以选择随机位置)的元素作为基准元素
        //比如new Random().nextInt(endIndex);
        int pivot = input[startIndex];
        int left = startIndex;
        int right = endIndex;
        while (left != right) {
            //控制right指针比较并左移
            while(left<right && input[right] > pivot) {
                right--;
            }
            //控制left指针比较并左移
            while(left<right && input[left] <= pivot) {
                left++;
            }
            //交换left和right指针所指向的元素
            if (left < right) {
                int p = input[left];
                input[left] = input[right];
                input[right] = p;
            }
        }

        //pivot和指针重合点交换
        input[startIndex] = input[left];
        input[left] = pivot;

        return left;
    }

    public static void main(String[] args) {
        int[] input = {2,6,5,22,4,8,1};
        quickSortDoubleRound(input, 0, input.length - 1);
        System.out.println(Arrays.toString(input));
    }
}
