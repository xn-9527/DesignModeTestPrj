package cn.soulutions.addNumbers;

/**
 * 找到一个无序数组中找两个特定数，使其相加等于特定数字，请写代码java将它找出来，
 * 并指出时间复杂度。
 * 例如 【10,25,19,89,75,56,34,54,16，9，-5】找到相加等于28的【19，9 】
 * Created by xiaoni on 2020/3/30.
 */
public class AddNumbersTest {

    public static void main(String[] args) {
        int[] input = {10,25,19,89,75,56,34,54,16,9,-5};
        findTwoNumber(input, 28);
        findTwoNumber(input, 5);
        findTwoNumber(input, 20);
    }

    /**
     * 当input输入为升序，
     * 若最大值 + 次大值 < totalNum,则不存在
     * 若最小值 + 次小值 > totalNum,则不存在
     *
     * 快速排序的时间复杂度O(nlogn)
     * 双重for循环最差是O(n^2)
     *
     * @param input
     * @param totalNum
     */
    public static void findTwoNumber(int[] input, int totalNum) {
        if (input.length <= 1) {
            //不满足
            return;
        }
        int left = 0;
        int right = input.length - 1;
        quickSort(input, left, right);

        if (input[left] + input[left + 1] > totalNum) {
            return;
        }
        if (input[right - 1] + input[right] < totalNum) {
            return;
        }

        int startNumber;
        int endNumber;
        Loop:
        for (right = input.length - 1;right >=0; right--) {
            for (left = 0; left < right; left ++) {
                startNumber = input[left];
                endNumber = input[right];
                if (startNumber + endNumber == totalNum) {
                    System.out.println(startNumber + "," + endNumber);
                    break Loop;
                }
            }
        }

        System.out.println("finished");
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

    public static void swap(int[] input, int left, int right) {
        int temp = input[left];
        input[left] = input[right];
        input[right] = temp;
    }

}
