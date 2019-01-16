package cn.test.singleton;

/**
 * Created by xiaoni on 2019/1/16.
 */
public class Elvis {
    //第一种方法，public的对象实例
    public static final Elvis INSTASNCE = new Elvis();
    private Elvis(){}

    public void leaveTheBuilding() {}
}
