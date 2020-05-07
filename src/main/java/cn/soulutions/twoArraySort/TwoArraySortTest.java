package cn.soulutions.twoArraySort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
public class TwoArraySortTest {
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //System.out.println("arr1 length");
        //int a = in.nextInt();
        //System.out.println(a);
        //System.out.println("Hello World!");
        int[] arr1 = {2,3,1,3,2,4,6,7,9,2,19};
        int[] arr2 = {2,1,4,3,9,6};
        List<Integer> output = solution(arr1, arr2);
        System.out.println(output);
        int[] arr3 = {2};
        int[] arr4 = {2};
        List<Integer> output2 = solution(arr3, arr4);
        System.out.println(output2);
        int[] arr5 = {2,-1};
        int[] arr6 = {2};
        List<Integer> output3 = solution(arr5, arr6);
        System.out.println(output3);
        int[] arr7 = {2,-1,-2,Integer.MAX_VALUE,Integer.MIN_VALUE};
        int[] arr8 = {-1,Integer.MIN_VALUE};
        List<Integer> output4 = solution(arr7, arr8);
        System.out.println(output4);
    }

    public static List<Integer> solution(int[] arr1, int[] arr2) {
        List<Integer> output = new ArrayList<>(arr1.length);
        Map<Integer,Integer> arr1Map = new TreeMap<>();
        for (int i = 0; i < arr1.length; i ++) {
            int key = arr1[i];
            Integer temp1 = arr1Map.get(key);
            if (temp1 == null) {
                temp1 = 0;
            }
            arr1Map.put(key, ++temp1);
        }
        for (int j = 0; j < arr2.length;j++) {
            int key2 = arr2[j];
            int value = arr1Map.get(key2);
            for (int k = 0; k < value; k++) {
                output.add(key2);
            }
            arr1Map.remove(key2);
        }
        if (arr1Map.size() > 0) {
            arr1Map.forEach((k,v)-> {
                for (int l = 0; l < v; l++) {
                    output.add(k);
                }
            });
        }
        return output;
    }
}
