package cn.adapter;

/**
 * Created by xiaoni on 2019/8/28.
 * 源接口（已经存在的接口）
 需要被转换的对象
 这个接口需要重新配置以适应目标接口
 */
public class Source {
    public void specifiRequest() {
        System.out.println("源接口对象调用源接口中的方法");
    }
}
