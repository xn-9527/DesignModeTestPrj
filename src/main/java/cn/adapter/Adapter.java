package cn.adapter;

/**
 * Created by xiaoni on 2019/8/28.
 */
public class Adapter implements Target {
    //持有源接口对象
    private Source source;

    /**
     * 构造方法，传入需要被适配的对象
     * @param source
     */
    public Adapter(Source source) {
        this.source = source;
    }

    /**
     * 重写目标接口的方法，以适应客户端的需求
     */
    @Override
    public void request() {
        //调用源接口的方法
        System.out.println("适配器包装源接口对象，调用源接口的方法");
        source.specifiRequest();
    }
}
