package cn.sort.bubbleSort;

/**
 * @Author: xiaoni
 * @Date: 2020/3/17 23:05
 */
public class BubbleSortTest {

    public static void sort(int[] array) {
        for(int i = 0; i < array.length - 1; i++) {
            //有序标记，每一轮的初始值都是true
            boolean isSorted = true;
            for (int j = 0; j < array.length -i -1;j++) {
                int tmp = 0;
                if (array[j] > array[j+1]) {
                    tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] =tmp;
                    //因为有元素进行了交互，所以不算有序的，标记变为false
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] input = {1,5,3,4,2};
        sort(input);
        for (int i = 0; i < input.length;i++) {
            System.out.println(input[i]);
        }
    }
}
