package cn.test.bitcal;

/**
 * java提供两种右移运算符，属于位运算符。位运算符用来对二进制位进行操作。
 * >>  ：算术右移运算符，也称带符号右移。用最高位填充移位后左侧的空位。
 * >>>：逻辑右移运算符，也称无符号右移。只对位进行操作，用0填充左侧的空位。
 * @author Chay
 * @date 2021/6/4 15:47
 */
public class BitCalculateTest {
    public static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * @see java.util.concurrent.ConcurrentHashMap#tableSizeFor
     * @param c
     * @return
     */
    public static int tableSizeFor(int c) {
        int n = c - 1;
        System.out.println("n=" + n);
        n |= n >>> 1;
        System.out.println("n >>> 1=" + n);
        n |= n >>> 2;
        System.out.println("n >>> 2=" + n);
        n |= n >>> 4;
        System.out.println("n >>> 4=" + n);
        n |= n >>> 8;
        System.out.println("n >>> 8=" + n);
        n |= n >>> 16;
        System.out.println("n >>> 16=" + n);
        int result = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        tableSizeFor(2);
        System.out.println("=========================");
        tableSizeFor(7);
        System.out.println("=========================");
        tableSizeFor(8);
        System.out.println("=========================");
        tableSizeFor(9);
    }
}
