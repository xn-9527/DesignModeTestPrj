package cn.test.uuid;

import java.util.UUID;

/**
 * @author: chay.ni
 * @date: 2021/4/19 14:54 
 * @description:
 */
public class UUIDTest {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
