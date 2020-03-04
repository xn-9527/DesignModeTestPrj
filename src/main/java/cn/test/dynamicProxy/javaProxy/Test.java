package cn.test.dynamicProxy.javaProxy;

/**
 * Created by xiaoni on 2020/3/4.
 */
public class Test {
    public static void main(String[] args) {
        MyProxy myProxy = new MyProxy();
        IHouse iHouseA = myProxy.getInstance(new HouseA());
        iHouseA.rent();

        IHouse iHouseB = myProxy.getInstance(new HouseB());
        iHouseB.rent();
    }
}
