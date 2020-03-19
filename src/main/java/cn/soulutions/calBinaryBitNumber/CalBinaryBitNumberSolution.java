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
            System.out.println(n);
            count++;
            n=n&(n-1);
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("#####" + NumberOf1(1));
        System.out.println("#####" + NumberOf1(3));
        System.out.println("#####" + NumberOf1(4));
        System.out.println("#####" + NumberOf1(-1));
    }
}
