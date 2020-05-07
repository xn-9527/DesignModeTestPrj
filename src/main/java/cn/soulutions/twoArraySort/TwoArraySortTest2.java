package cn.soulutions.twoArraySort;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given two arrays arr1 and arr2, the elements of arr2 are distinct, and all elements in arr2 are also in arr1.
 * Sort the elements of arr1 such that the relative ordering of items in arr1 are the same as in arr2.  Elements that don't appear in arr2 should be placed at the end of arr1 in ascending order.
 * Example 1:
 * Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19],
 * arr2 = [2,1,4,3,9,6] Output: [2,2,2,1,4,3,3,9,6,7,19]
 *
 * @author Chay
 * @date 2020/4/27 9:50
 */
public class TwoArraySortTest2 {
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //System.out.println("arr1 length");
        //int a = in.nextInt();
        //System.out.println(a);
        //System.out.println("Hello World!");
        Integer[] arr1 = {2,3,1,3,2,4,6,7,9,2,19};
        Integer[] arr2 = {2,1,4,3,9,6};
        Integer[] output = solution(arr1, arr2);
        System.out.println(Arrays.toString(output));
        Integer[] arr3 = {2};
        Integer[] arr4 = {2};
        Integer[] output2 = solution(arr3, arr4);
        System.out.println(Arrays.toString(output2));
        Integer[] arr5 = {2,-1};
        Integer[] arr6 = {2};
        Integer[] output3 = solution(arr5, arr6);
        System.out.println(Arrays.toString(output3));
        Integer[] arr7 = {2,-1,-2,Integer.MAX_VALUE,Integer.MIN_VALUE, null};
        Integer[] arr8 = {-1,Integer.MIN_VALUE, null};
        Integer[] output4 = solution(arr7, arr8);
        System.out.println(Arrays.toString(output4));
    }

    public static Integer[] solution(Integer[] arr1, Integer[] arr2) {
        final List<Integer> arr2List = Arrays.asList(arr2);
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == null && o2 != null) {
                    return -1;
                } else if (o1 == null && o2 == null) {
                    return 0;
                } else if (o1 != null && o2 == null){
                    return 1;
                }
                //三种情况:
                //1.一个存在一个不存在
                boolean isO1Exist = arr2List.contains(o1);
                boolean isO2Exist = arr2List.contains(o2);
                if (isO1Exist && !isO2Exist) {
                    return -1;
                } else if (!isO1Exist && isO2Exist) {
                    return 1;
                }
                //2.两个都存在
                else if (isO1Exist && isO2Exist) {
                    int index1 = arr2List.indexOf(o1);
                    int index2 = arr2List.indexOf(o2);
                    if (index1 == index2) {
                        return 0;
                    } else if (index1 > index2) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                //3.两个都不存在
                else {
                    if (o1.equals(o2)) {
                        return 0;
                    } else if (o1 > o2) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        };
        Arrays.sort(arr1, comparator);
        return arr1;
    }
}
