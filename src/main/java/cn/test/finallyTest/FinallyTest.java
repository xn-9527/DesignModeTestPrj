package cn.test.finallyTest;

/**
 * @Author: xiaoni
 * @Date: 2020/5/8 1:15
 */
public class FinallyTest {
    public static void main(String[] args) {
        try {
            System.out.println("try");
        } catch (Exception e) {
            System.out.println("catch");
        } finally {
            System.out.println("finally");
        }
    }
}
