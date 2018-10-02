package test.toRomeNumber;

import java.util.Scanner;

/**
 * Created by xiaoni on 2018/10/1.
 */
public class ToRomeNumber {
    public final static String[] units = {"I","II","III","IV","V","VI","VII","VIII","IX"};
    public final static String[] decades = {"X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
    public final static String[] hundreds = {"C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
    public final static String[] thousands = {"M","MM","MMM"};

    /**
     * 把1-3999的数组转换成罗马数字
     * @param integer
     * @return
     */
    public static String toRomeNumber(Integer integer) {
        if(integer == null || integer < 1 || integer > 3999) {
            System.out.println("请输入1-3999的整数!");
            return null;
        }

        StringBuilder result = new StringBuilder();
        int thousand = integer / 1000;
        int thousandRest = integer % 1000;
        if (thousand > 0 && thousand < 4) {
            result.append(thousands[thousand - 1]);
        }
        int hundred = thousandRest / 100;
        int hundredRest = thousandRest % 100;
        if (hundred > 0 && hundred < 9) {
            result.append(hundreds[hundred - 1]);
        }
        int decade = hundredRest / 10;
        int decadeRest = hundredRest % 10;
        if (decade > 0 && decade < 9) {
            result.append(decades[decade - 1]);
        }
        int unit = decadeRest;
        if (unit > 0 && unit < 9) {
            result.append(units[unit - 1]);
        }
        return result.toString();
    }


    public static void main(String[] args) {
        String input = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入1-3999的整数：");
            try {
                input = scanner.nextLine();
                System.out.println(toRomeNumber(Integer.parseInt(input)));
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("非法输入，请输入1-3999的整数！");
            }
        }
    }
}
