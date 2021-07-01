package cn.soulutions.longestMirrorString;

/**
 * @author: chay
 * @date: 2021/7/1 22:03 
 * @description:
 */
public class LongestMirrorStringTest {
    /**
     题目 1
     求最长回文子串：
     输入一个字符串，求它的最长回文子串，回文子串的含义是：
     正着看和反着看都相同， 如abba和yyxyy,判断时忽略大小写，输出保持原样
     样例输入：KobeBryantsayMadamImAdamXXYXX
     样例输出:  MadamImAdam
     */
    public static char[] maxString(char[] input) {
        if (input.length <= 0) {
            return new char[0];
        }
        int resultLeft = 0;
        int resultRight = 0;
        int lastMaxLength = 0;
        int left = 0;
        int length = input.length;
        int right = input.length - 1;
        while(left < length) {
            while (right >= left) {
                int i = 0;
                int thisMaxLength = 0;
                while (left + i < length && (right - i) >= (left + i) && String.valueOf(input[left + i]).equalsIgnoreCase(String.valueOf(input[right - i]))) {
                    thisMaxLength ++;
                    i ++;
                }
                if (thisMaxLength > lastMaxLength) {
                    lastMaxLength = thisMaxLength;
                    resultLeft = left;
                    resultRight = right;
                }
                right --;
            }
            left ++;
            right = length - 1;
        }
        //output
        char[] output = new char[resultRight - resultLeft + 1];
        for (int i = resultLeft; i < resultRight+1; i ++) {
            output[i-resultLeft] = input[i];
        }
        System.out.println(lastMaxLength);
        return output;
    }


    public static void main(String[] args) {
        System.out.println(maxString("KobeBryantsayMadamImAdamXXYXX".toCharArray()));
    }

}
