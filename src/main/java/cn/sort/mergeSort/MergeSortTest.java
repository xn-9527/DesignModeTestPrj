package cn.sort.mergeSort;

import java.util.Arrays;

/**
 * @author Chay
 * @date 2021/6/8 17:22
 */
public class MergeSortTest {
    public static int[] mergeSort(int[] input, int left, int right) {
        if (left == right) {
            return new int[]{input[right]};
        }
        int mid = (right + left)/2;
        //左半边归并排序递归
        int[] leftArr = mergeSort(input, left, mid);
        //右半边归并排序递归
        int[] rightArr = mergeSort(input, mid + 1, right);
        //新的存放结果数组
        int[] newResultArr = new int[leftArr.length + rightArr.length];
        int m=0,i=0,j=0;
        while(i < leftArr.length && j < rightArr.length) {
            //把小元素归并到结果数组中
            newResultArr[m++] = leftArr[i] < rightArr[j] ? leftArr[i++] : rightArr[j++];
        }
        //左右子序列剩余的元素直接合并到结果数组中
        while(i < leftArr.length) {
            newResultArr[m++] = leftArr[i++];
        }
        while(j < rightArr.length) {
            newResultArr[m++] = rightArr[j++];
        }
        return newResultArr;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeSort(new int[]{2,1,3,8,4,5,3}, 0, 6)));
    }
}
