package cn.soulutions.correctWords;

import java.util.Scanner;

/**
 * 1. 三个同样的字母连在一起，一定是拼写错误，去掉一个的就好啦：比如 helllo -> hello
 * 2. 两对一样的字母（AABB型）连在一起，一定是拼写错误，去掉第二对的一个字母就好啦：比如 helloo -> hello
 * 3. 上面的规则优先“从左到右”匹配，即如果是AABBCC，虽然AABB和BBCC都是错误拼写，应该优先考虑修复AABB，结果为AABCC
 * @author Chay
 * @date 2020/3/20 15:03
 */
public class CorrectWordsTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = sc.next();
        }

        checkWords(words);
    }

    public static void checkWords(String[] words) {
        for (String word : words) {
            char[] wordChars = word.toCharArray();
            char[] correctChars = new char[wordChars.length];
            int formalCharCount = 1;
            int nowCount = 1;
            char formalChar = ' ';
            char now = ' ';
            int j = 1;
            //第一个只管放进去
            correctChars[0] = wordChars[0];
            for (int i = 1; i < wordChars.length; i ++) {
                formalChar = wordChars[i - 1];
                now = wordChars[i];
                //前一个和后面一个不相等，则直接拼接
                if (formalChar != now) {
                    correctChars[j] = now;
                    j++;

                    formalCharCount = nowCount;
                    nowCount = 1;
                } else {
                    //前一个和当前的相等
                    nowCount ++;
                    //如果当前的不是三连，且没有AABB，则添加当前字符，否则丢弃
                    if ((formalCharCount <= 2 && nowCount > 2) || (formalCharCount >=2 && nowCount >= 2)) {
                        //三连或AABB抛弃,回滚nowCount
                        nowCount--;
                        continue;
                    }
                    correctChars[j] = now;
                    j++;
                }
            }
            System.out.println(String.valueOf(correctChars).trim());
        }
    }
}
