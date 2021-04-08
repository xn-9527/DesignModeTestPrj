package cn.test.hash;

/**
 * @author: chay
 * @date: 2021/4/7 21:49 
 * @description: 通过黄金分割魔数生成非常均衡的散列值,不会重复
 */
public class TestHash {

    /**
     * The difference between successively generated hash codes - turns
     * implicit sequential thread-local IDs into near-optimally spread
     * multiplicative hash values for power-of-two-sized tables.
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    public static void magicHash(int size) {
        int hashCode = 0;
        for (int i = 0; i< size; i ++) {
            hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
            System.out.print((hashCode & (size -1)) + "  ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        magicHash(16);
        magicHash(32);
    }
}
