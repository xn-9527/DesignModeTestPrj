package test.toRomeNumber;

/**
 * Created by xiaoni on 2018/10/2.
 */
public class Solution {
    public String intToRoman(int num) {
        String str = "";
        int i = 1, n;  //i表示从右到左整数第几位
        while (num > 0) {
            n = num % 10;  //表示数位上的数字
            num /= 10;
            str = intToCharacter(n, i).concat(str);  //把每一位返回的字符串和str相连
            i++;
        }
        return str;
    }

    public String intToCharacter(int n, int i) {//n表示特定的输入，i表示个位十位百位或者千位
        String str1, str2, str3;//拿0~9之间的数举例，str1表示I，str2表示V，str3表示X
        String str = "";//这个函数返回特定位上的罗马数字表示形式
        if (1 == i) {            //下面if语句是判断n是哪一位上的,依次用罗马数字表示1、5、10
            str1 = "I";        //个位上的数
            str2 = "V";
            str3 = "X";
        } else if (2 == i) {
            str1 = "X";       //十位上的数
            str2 = "L";
            str3 = "C";
        } else if (3 == i) {
            str1 = "C";       //百位上的数
            str2 = "D";
            str3 = "M";
        } else {
            str1 = "M";     //千位上的数，因为最大数到3999，所以str2和str3为空
            str2 = "";
            str3 = "";
        }
        switch (n) {         //switch对各数字进行组合以个位上的数举例如下
            case 0:
                break;           //空
            case 1:
                str += str1;
                break;    //I
            case 2:
                str += str1 + str1;
                break;  //II
            case 3:
                str += str1 + str1 + str1;
                ;
                break;//III
            case 4:
                str += str1 + str2;
                break;     //IV
            case 5:
                str += str2;
                break;           //V
            case 6:
                str += str2 + str1;
                break;      //VI
            case 7:
                str += str2 + str1 + str1;
                break;    //VII
            case 8:
                str += str2 + str1 + str1 + str1;
                break;    //VIII
            case 9:
                str += str1 + str3;
                break;          //IX
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().intToRoman(1234));
    }
}
