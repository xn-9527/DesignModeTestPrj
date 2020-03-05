package cn.test.dynamicProxy.javaProxy;

/**
 * Created by xiaoni on 2020/3/4.
 */
public final class FinalHouseC implements IHouse {
    @Override
    public int rent() {
        System.out.println("B rent for 2000");
        return 2000;
    }

    @Override
    public final void onlyForYou() {
        System.out.println("我只为B");
    }
}
