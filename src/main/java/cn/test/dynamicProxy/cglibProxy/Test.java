package cn.test.dynamicProxy.cglibProxy;

import cn.test.dynamicProxy.javaProxy.FinalHouseC;
import cn.test.dynamicProxy.javaProxy.HouseA;

/**
 * Created by xiaoni on 2020/3/4.
 */
public class Test {
    public static void main(String[] args) {
        HouseA houseA = (HouseA) new CglibProxy().getInstance(HouseA.class);
        houseA.rent();
        /**
         * CGLib不能代理final的方法(无法增强)
         */
        houseA.onlyForYou();

        System.out.println("#####################");
        /**
         * CGLib不能代理final的类，java.lang.IllegalArgumentException: Cannot subclass final class cn.test.dynamicProxy.javaProxy.FinalHouseC
         */
        FinalHouseC houseC = (FinalHouseC) new CglibProxy().getInstance(FinalHouseC.class);
        houseC.rent();
        houseC.onlyForYou();
    }
}
