package cn.tree.ergodic.Permutation;

import java.util.*;

/**
 * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 *
 * @Author: xiaoni
 * @Date: 2020/3/18 0:03
 */
public class Permutation2Test {

    public static ArrayList<String> Permutation(String str) {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(permutationInner(str));
        return result;
    }
    /**
     * 思想是[f(n)的所有情况] = (a,b,c,d所有字母) + [下一次f(n-1)的所有情况],每一轮比上一轮少一个字母，递归下去
     *
     * @param str
     * @return
     */
    public static Set<String> permutationInner(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 1) {
            return Collections.singleton(str);
        }
        //输出顺序不对也算错，这个TreeSet是关键
        Set<String> result = new TreeSet<>();
        for (int i = 0; i < str.length(); i++) {
            //都跟第一个交换
            str = swap(str, 0, i);
            String first = str.substring(0, 1);
            Set<String> lastResult = permutationInner(str.substring(1));
            if (lastResult != null && !lastResult.isEmpty()) {
                for (String last : lastResult) {
                    result.add(first + last);
                }
            }
        }
        return result;
    }

    public static String swap(String str, int a, int b) {
        char[] x = str.toCharArray();
        char t = x[a];
        x[a] = x[b];
        x[b] = t;
        return String.valueOf(x);
    }

    public static void main(String[] args) {
        System.out.println(Permutation("abc").toString());
    }
}
