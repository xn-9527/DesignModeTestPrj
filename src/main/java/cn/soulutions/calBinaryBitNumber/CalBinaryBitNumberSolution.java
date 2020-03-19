package cn.soulutions.calBinaryBitNumber;

/**
 * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
 *
 * @author Chay
 * @date 2020/3/18 23:16
 */
public class CalBinaryBitNumberSolution {
    public static int NumberOf1(int n) {
        int count=0;
        while(n!=0){
//            System.out.println(n);
            count++;
            n=n&(n-1);
        }
        return count;
    }

    public static int NumberOf12(int n) {
        int count=0;
        if (n == 0) {
            return 0;
        }
        //事实证明，通过Integer.toBinaryString()方法返回的结果，就是补码了
        String complement = complement(n);
        char[] chars = complement.toCharArray();
        for (char c : chars) {
            if("1".equals(String.valueOf(c))) {
                count ++;
            }
        }
        return count;
    }

    //计算补码
    public static String complement(int n) {
        if (n >= 0) {
            return Integer.toBinaryString(n);
        } else {
            //他的正数
            int abs = -n;
            //负数补码是符号位不变，然后正数值当中第一个1前面的数字按位取反，或者全部取反后+1
            return Integer.toBinaryString(~abs + 0B1);
        }
    }

    public static void main(String[] args) {
        System.out.println("#####" + NumberOf1(1));
        System.out.println("====" + NumberOf12(1));
        System.out.println("#####" + NumberOf1(3));
        System.out.println("====" + NumberOf12(3));
        System.out.println("#####" + NumberOf1(4));
        System.out.println("====" + NumberOf12(4));
        System.out.println("#####" + NumberOf1(-1));
        System.out.println("====" + NumberOf12(-1));
        System.out.println("&&&&&&&" + complement(-1));
        System.out.println("^^^^^^^" + Integer.toBinaryString(-1));
        System.out.println("&&&&&&&" + complement(-2));
        System.out.println("^^^^^^^" + Integer.toBinaryString(-2));
        System.out.println(~1);
        System.out.println(~2);
        System.out.println(~-1);
        System.out.println(~0B1);
        System.out.println(Integer.parseInt("10011", 2));


    }
}
