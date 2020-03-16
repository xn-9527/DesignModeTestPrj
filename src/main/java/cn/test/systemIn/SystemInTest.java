package cn.test.systemIn;

import java.io.IOException;
import java.util.Scanner;

/**
 * @Author: xiaoni
 * @Date: 2020/3/16 20:26
 */
public class SystemInTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入：");
        while (true) {
            try {
                if (System.in.available() > 0) {
                    System.out.println(sc.nextLine().replace("?", "!"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
