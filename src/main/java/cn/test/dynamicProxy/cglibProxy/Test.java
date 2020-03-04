package cn.test.dynamicProxy.cglibProxy;

import cn.test.dynamicProxy.javaProxy.HouseA;

/**
 * Created by xiaoni on 2020/3/4.
 */
public class Test {
    public static void main(String[] args) {
        HouseA houseA = (HouseA) new CglibProxy().getInstance(HouseA.class);
        houseA.rent();
    }
}
