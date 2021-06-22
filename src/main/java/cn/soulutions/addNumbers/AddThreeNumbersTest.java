package cn.soulutions.addNumbers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero
 * Note: The solution set must not contain duplicate triplets.
 *
 * For example, given array S = [-1, 0, 1, 2, -1, -4],
 *
 * A solution set is:
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 * ————————————————
 * 版权声明：本文为CSDN博主「Daisy_HJL」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq_28618765/article/details/65979416
 *
 * @author Chay
 * @date 2021/6/22 11:09
 */
public class AddThreeNumbersTest {
    /**
     * 这个想法是对输入数组进行排序，然后遍历三元组的可能第一个元素的所有索引。
     * 对于每个可能的第一个元素，我们进行阵列剩余部分的标准双向2Sum扫描。
     * 同样，我们想跳过相等的元素，以避免在答案中重复，而不会像这样设置或混淆
     * @param input
     * @return
     */
    public static List<List<Integer>> soulution(int[] input) {
        Arrays.sort(input);
        List<List<Integer>> result = new LinkedList<>();

        for(int i = 0; i< input.length; i++) {
            //第一个元素，或当前元素和上一个不同
            if (i == 0 || (i > 0 && input[i] != input[i - 1])) {
                int sum = 0 - input[i];
                int l = i + 1;
                int h = input.length - 1;
                while (l < h) {
                    if(input[l] + input[h] == sum) {
                        result.add(Arrays.asList(input[i], input[l], input[h]));
                        while ((l < h) && (input[l] == input[l+1])) {
                            //跳过左边的与当前左元素相同元素，因为上面排过序了
                            l++;
                        }
                        while ((l < h) && (input[l] == input[h-1])) {
                            //跳过右边的与当前左元素相同元素，因为上面排过序了
                            h--;
                        }
                        //取下一个不同与当前左元素的左边元素，因为上面排过序了
                        l++;
                        //取下一个不同于当前左元素的右边元素，因为上面排过序了
                        h--;
                    } else if (input[l] + input[h] < sum) {
                        //和不够，加大左边元素，因为上面排过序了
                        l++;
                    } else {
                        //和超了，减小右边元素，因为上面拍过序了
                        h--;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] input = new int[]{-1, 1, -1, 0, 2, 3 , -1, 1, -2, -4};
        System.out.println(AddThreeNumbersTest.soulution(input));
    }
}
