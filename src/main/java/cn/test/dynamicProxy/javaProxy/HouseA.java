package cn.test.dynamicProxy.javaProxy;

/**
 * Created by xiaoni on 2020/3/4.
 */
public class HouseA implements IHouse {
    @Override
    public int rent() {
        System.out.println("A rent for 1000");
        return 1000;
    }
}
