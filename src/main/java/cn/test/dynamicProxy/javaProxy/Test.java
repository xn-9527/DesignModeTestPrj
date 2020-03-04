package cn.test.dynamicProxy.javaProxy;

/**
 * Created by xiaoni on 2020/3/4.
 */
public class Test {
    public static void main(String[] args) {
        JdkProxy jdkProxy = new JdkProxy();
        IHouse iHouseA = jdkProxy.getInstance(new HouseA());
        iHouseA.rent();

        IHouse iHouseB = jdkProxy.getInstance(new HouseB());
        iHouseB.rent();
    }
}
