package cn.soulutions.phoneKeyBoard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合，按照字典序升序排序,如果有重复的结果需要去重
 *
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 *
 *
 * 输入描述:
 * 输入2-9数字组合， 字符串长度 1<=length<=20
 *
 * 输出描述:
 * 输出所有组合
 *
 * 输入例子1:
 * 23
 *
 * 输出例子1:
 * [ad, ae, af, bd, be, bf, cd, ce, cf]
 *
 * 输入例子2:
 * 92
 *
 * 输出例子2:
 * [wa, wb, wc, xa, xb, xc, ya, yb, yc, za, zb, zc]
 *
 * 输入例子3:
 * 458
 *
 * 输出例子3:
 * [gjt, gju, gjv, gkt, gku, gkv, glt, glu, glv, hjt, hju, hjv, hkt, hku, hkv, hlt, hlu, hlv, ijt, iju, ijv, ikt, iku, ikv, ilt, ilu, ilv]
 *
 * @author Chay
 * @date 2020/3/20 18:18
 */
public class PhoneKeyBoardTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();

        System.out.println(solution(input));
    }

    //对照表
    public final static Map<String, String> CHECK_LIST = new HashMap<String, String>(8) {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};

    /**
     * 采用递归思想，f(n)=n的全部情况*f(n-1)
     *
     * @param input
     * @return
     */
    public static LinkedList<String> solution(String input) {
        //LinkedList链表方便扩容
        LinkedList<String> result = new LinkedList<String>();
        if (input.length() == 1) {
            String values = CHECK_LIST.get(input);
            char[] chars = values.toCharArray();
            for (char c : chars) {
                result.add(String.valueOf(c));
            }
            return result;
        }
        char[] chars = input.toCharArray();
        //每次取第一个
        String iTemp = CHECK_LIST.get(String.valueOf(chars[0]));
        char[] iChars = iTemp.toCharArray();
        //递归后面的字符串
        LinkedList<String> lastResult = solution(input.substring(1));
        for(char c : iChars) {
            for (String s : lastResult) {
                result.add(String.valueOf(c) + s);
            }
        }
        return result;
    }
}
