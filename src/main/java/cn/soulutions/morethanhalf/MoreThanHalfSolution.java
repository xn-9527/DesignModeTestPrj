package cn.soulutions.morethanhalf;

import java.util.HashMap;
import java.util.Map;

/**
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
 *
 * @author Chay
 * @date 2020/3/18 21:43
 */
public class MoreThanHalfSolution {

    public static int MoreThanHalfNum_Solution(int [] array) {
        int length = array.length;
        int half = length / 2;
        Map<Integer, Integer> count = new HashMap<>();
        for (int i : array) {
            Integer c = count.computeIfAbsent(i, k -> 0);
            c++;
            count.put(i, c);
        }

        int maxKey = -1;
        int maxCount = -1;
        for(Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (entry.getValue() > maxCount) {
                //更新最大值
                maxKey = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        if (maxCount > half) {
            return maxKey;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        int[] input = {1,2,3,4,5,6,2,2,2,2,2};
        System.out.println(MoreThanHalfNum_Solution(input));
    }
}
