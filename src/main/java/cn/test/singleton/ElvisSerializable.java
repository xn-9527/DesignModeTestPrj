package cn.test.singleton;

/**
 * Created by xiaoni on 2019/1/16.
 */
public class ElvisSerializable {
    //无论那种方法，反序列化时为了保证唯一，必需声明所有的实例都是瞬时(Transient)的
    public static final ElvisSerializable INSTASNCE = new ElvisSerializable();
    private ElvisSerializable(){}

    public void leaveTheBuilding() {}

    //readResolve method to preserver singleton property
    private Object readResolve() {
        //Return the one true Elvis and let the garbage collector
        //take care of the Elvis impersonator
        return INSTASNCE;
    }
}
