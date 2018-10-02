package test.toRomeNumber;

import java.util.ArrayList;
import java.util.List;
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
    public static String toRomeNumber(Integer integer) throws Exception{
        if (integer == null || integer < 1 || integer > 3999){
            System.out.println("请输入1-3999的整数!");
            return null;
        }

        StringBuilder result = new StringBuilder();

        /**
         * 第一版
         */
        /*int thousand = integer / 1000;
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
        }*/

        /**
         * 第二版,提取成公共函数
         */
        int remainder = integer;
        for(int i = 3;i > -1;i --) {
            if (i == 3) {
                remainder = calResultByDigit(remainder, i, 0, 4, result);
            } else {
                remainder = calResultByDigit(remainder, i, 0, 9, result);
            }
        }
        return result.toString();
    }

    /**
     * 计算某位数对应的罗马数字，并返回余数
     * @param number
     * @param digit
     * @param downThreadHold
     * @param upThreadHold
     * @param result
     * @return
     */
    public static Integer calResultByDigit(Integer number,
                                           Integer digit,
                                           Integer downThreadHold,
                                           Integer upThreadHold,
                                           StringBuilder result) throws Exception{
        String[] digitRomes = getDigitRomes(digit);
        int divisor = (int) Math.pow(10, digit);
        int multiple = number / divisor;
        int remainder = number % divisor;
        if (multiple > downThreadHold && multiple < upThreadHold) {
            result.append(digitRomes[multiple - 1]);
        }
        return remainder;
    }

    /**
     * 根据位数，返回对应的罗马数字模版
     * @param digit
     * @return
     */
    private static String[] getDigitRomes(Integer digit) throws Exception{
        switch (digit) {
            case 0:
//                return units;
                return getDigitRomesModel("I", "V", "X");
            case 1:
//                return decades;
                return getDigitRomesModel("X", "L", "C");
            case 2:
//                return hundreds;
                return getDigitRomesModel("C", "D", "M");
            case 3:
//                return thousands;
                return getDigitRomesModel("M", null, null);
            default:
                return null;
        }
    }

    /**
     * 生成罗马对应位数0-9，如果middle为空，则只生成前三个数，如果high为空，则只生成前8个数
     * @param low
     * @param middle
     * @param high
     * @return
     * @throws Exception
     */
    private static String[] getDigitRomesModel(String low,
                                               String middle,
                                               String high) throws Exception{
        List<String> result = new ArrayList<String>();
        //generate 0-3 digitRomes
        if (low == null) {
            return result.toArray(new String[result.size()]);
        }
        StringBuilder lowRomes = new StringBuilder();
        for (int i = 0;i < 3;i ++) {
            lowRomes.append(low);
            result.add(lowRomes.toString());
        }
        //generate 4-8 digitRomes
        if (middle == null) {
            return result.toArray(new String[result.size()]);
        }
        //4
        result.add(low + middle);
        //5
        result.add(middle);
        StringBuilder middleRomes = new StringBuilder();
        middleRomes.append(middle);
        //generate 6-8
        for (int i = 0;i < 3;i ++) {
            middleRomes.append(low);
            result.add(middleRomes.toString());
        }
        //generate 9
        if (high == null) {
            return result.toArray(new String[result.size()]);
        }
        result.add(middle + high);
        return result.toArray(new String[result.size()]);
    }

    public static void main(String[] args) {
        try {
            System.out.println(getDigitRomesModel("I", "V", "X"));
            System.out.println(getDigitRomesModel("X", "L", "C"));
            System.out.println(getDigitRomesModel("C", "D", "M"));
            System.out.println(getDigitRomesModel("M", null, null));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


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
            } finally {
            }
        }
    }
}
