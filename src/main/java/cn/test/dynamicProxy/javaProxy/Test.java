package cn.test.dynamicProxy.javaProxy;

/**
 * Created by xiaoni on 2020/3/4.
 */
public class Test {
    public static void main(String[] args) {
        JdkProxy jdkProxy = new JdkProxy();
        IHouse iHouseA = jdkProxy.getInstance(new HouseA());
        iHouseA.rent();
        iHouseA.onlyForYou();

        System.out.println("####################");
        IHouse iHouseB = jdkProxy.getInstance(new HouseB());
        iHouseB.rent();
        iHouseB.onlyForYou();

        System.out.println("####################");
        IHouse iHouseC = jdkProxy.getInstance(new FinalHouseC());
        iHouseC.rent();
        iHouseC.onlyForYou();
    }
}
