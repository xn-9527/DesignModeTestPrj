package cn.soulutions.ipcheck;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 编写一个函数来验证输入的字符串是否是有效的 IPv4 或 IPv6 地址
 *
 * IPv4 地址由十进制数和点来表示，每个地址包含4个十进制数，其范围为 0 - 255， 用(".")分割。比如，172.16.254.1；
 * 同时，IPv4 地址内的数不会以 0 开头。比如，地址 172.16.254.01 是不合法的。
 *
 * IPv6 地址由8组16进制的数字来表示，每组表示 16 比特。这些组数字通过 (":")分割。比如,  2001:0db8:85a3:0000:0000:8a2e:0370:7334 是一个有效的地址。而且，我们可以加入一些以 0 开头的数字，字母可以使用大写，也可以是小写。所以， 2001:db8:85a3:0:0:8A2E:0370:7334 也是一个有效的 IPv6 address地址 (即，忽略 0 开头，忽略大小写)。
 *
 * 然而，我们不能因为某个组的值为 0，而使用一个空的组，以至于出现 (::) 的情况。 比如， 2001:0db8:85a3::8A2E:0370:7334 是无效的 IPv6 地址。
 * 同时，在 IPv6 地址中，多余的 0 也是不被允许的。比如， 02001:0db8:85a3:0000:0000:8a2e:0370:7334 是无效的。
 *
 * 说明: 你可以认为给定的字符串里没有空格或者其他特殊字符。
 *
 * 输入描述:
 * 一个IP地址字符串
 *
 * 输出描述:
 * ip地址的类型，可能为
 *
 * IPv4,   IPv6,   Neither
 *
 * 输入例子1:
 * 172.16.254.1
 *
 * 输出例子1:
 * IPv4
 *
 * 例子说明1:
 * 这是一个有效的 IPv4 地址, 所以返回 "IPv4"
 *
 * 输入例子2:
 * 2001:0db8:85a3:0:0:8A2E:0370:7334
 *
 * 输出例子2:
 * IPv6
 *
 * 例子说明2:
 * 这是一个有效的 IPv6 地址, 所以返回 "IPv6"
 *
 * 输入例子3:
 * 256.256.256.256
 *
 * 输出例子3:
 * Neither
 *
 * 例子说明3:
 * 这个地址既不是 IPv4 也不是 IPv6 地址
 *
 * @author Chay
 * @date 2020/3/20 18:48
 */
public class IPCheckTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.next();

        checkIp(input);
    }

    private final static String IPV4_PROTOCOL = "^((25[0-5]丨2[0-4][0-9]|[0-1][0-9][0-9]|0[1-9][0-9]|([0-9])]{1,2})([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})){3})$";
    private final static String IPV6_PROTOCOL = "^(([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}{1}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}{1}|((22[0-3]丨2[0-1][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(:[0-9A-Fa-f]{1,4}{1,2}|:((22[0-3]丨2[0-1][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(:[0-9A-Fa-f]{1,4}{1,3}|:((22[0-3]丨2[0-1][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})){3})|:))|(([0-9A-Fa-f]{1,4}:){3}(:[0-9A-Fa-f]{1,4}{1,4}|:((22[0-3]丨2[0-1][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})){3})|:))|(([0-9A-Fa-f]{1,4}:){2}(:[0-9A-Fa-f]{1,4}{1,5}|:((22[0-3]丨2[0-1][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})){3})|:))|(([0-9A-Fa-f]{1,4}:){1}(:[0-9A-Fa-f]{1,4}{1,6}|:((22[0-3]丨2[0-1][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})){3})|:))|(:(:[0-9A-Fa-f]{1,4}{1,7}|:((22[0-3]丨2[0-1][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|0[1 -9][0-9]|([0-9])]{1,2})){3})|:))$";

    private static void checkIp(String input) {
        String result = "Neither";
        if (input != null && input.length() > 8) {
//            if (Pattern.matches(IPV4_PROTOCOL, input)) {
            if (isIPv4(input)) {
                result = "IPv4";
//            } else if (Pattern.matches(IPV6_PROTOCOL, input)) {
            } else if (isIPv6(input)) {
                result = "IPv6";
            }
        }

        System.out.println(result);
    }

    private final static String NUMBER = "0123456789";
    private final static String LETTER = "abcdefABCDEF";

    private static boolean isIPv6(String input) {
        String[] split = input.split(":");
        if (split.length != 8) {
            return false;
        }
        for (int i = 0; i< 4; i++) {
            String temp = split[i];
            int length = temp.length();
            //长度大于4不合法
            if (length > 4) {
                return false;
            }
            char[] chars = temp.toCharArray();
            //非法字符不合法
            for (char c : chars) {
                String tempC = String.valueOf(c);
                if (!(NUMBER.contains(tempC) || LETTER.contains(tempC))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isIPv4(String input) {
        String[] split = input.split("\\.");
        if (split.length != 4) {
            return false;
        }
        for (int i = 0; i< 4; i++) {
            String temp = split[i];
            int length = temp.length();
            //长度大于3不合法
            if (length > 3) {
                return false;
            }
            char[] chars = temp.toCharArray();
            //多位数0开头不合法
            if (length > 1 && chars[0] == '0') {
                return false;
            }
            //非数字不合法
            for (char c : chars) {
                if (!NUMBER.contains(String.valueOf(c))) {
                    return false;
                }
            }
            Integer num = Integer.parseInt(temp);
            if (num < 0 || num > 255) {
                return false;
            }
        }
        return true;
    }
}
