package cn.test.singleton;

/**
 * Created by xiaoni on 2019/1/16.
 */
public class Elvis2 {
    //第二种方法：静态工厂方法
    private static final Elvis2 INSTASNCE = new Elvis2();
    private Elvis2(){}

    public static Elvis2 getInstance() {
        return INSTASNCE;
    }

    public void leaveTheBuilding() {}
}
