package cn.ergodic.Permutation;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 * @Author: xiaoni
 * @Date: 2020/3/18 0:03
 */
public class PermutationTest {
    public static ArrayList<String> permutation(String str) {
        ArrayList<String> result = new ArrayList<String>() ;
        if(str==null || str.length()==0) { return result ; }

        char[] chars = str.toCharArray() ;
        TreeSet<String> temp = new TreeSet<>() ;
        permutation(chars, 0, temp);
        result.addAll(temp) ;
        return result ;
    }

    public static void permutation(char[] chars, int begin, TreeSet<String> result) {
        if(chars==null || chars.length==0 || begin<0 || begin>chars.length-1) { return ; }

        if(begin == chars.length-1) {
            result.add(String.valueOf(chars)) ;
        }else {
            for(int i=begin ; i<=chars.length-1 ; i++) {
                swap(chars, begin, i) ;

                permutation(chars, begin+1, result);

                swap(chars, begin, i) ;
            }
        }
    }

    public static void swap(char[] x, int a, int b) {
        char t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

    public static void main(String[] args) {
        System.out.println(permutation("abcd").toString());
    }
}
