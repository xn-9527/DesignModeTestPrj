package cn.adapter;

import cn.test.httpProgressBar.AjaxResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by xiaoni on 2019/8/28.
 * //客户端调用适配器中的方法
 //适配器包装源接口对象，调用源接口的方法
 //源接口对象调用源接口中的方法

 优点
 目标类和适配者类解耦，增加了类的透明性和复用性，同时系统的灵活性和扩展性都非常好，
 更换适配器或者增加新的适配器都非常方便，符合“开闭原则”

 缺点
 过多的使用适配器，会让系统非常零乱，不易整体进行把握。比如，明明看到调用的是A接口，
 其实内部被适配成了B接口的实现

 应用案例
 Java语言的数据库连接工具JDBC，JDBC给出一个客户端通用的抽象接口，
 每一个具体数据库引擎（如SQL Server、Oracle、MySQL等）的JDBC驱动软件都是一个介于JDBC接口和数据库引擎接口之间的适配器软件

 用途
 想要复用一些现有的类，但是接口与复用环境要求不一致
 */
@Slf4j
public class TestClient {
    public static void main(String[] args){

        //创建源对象（被适配的对象）
        Source source = new Source();
        //利用源对象对象一个适配器对象，提供客户端调用的方法
        Adapter adapter = new Adapter(source);
        System.out.println("客户端调用适配器中的方法");
        adapter.request();

        HashMap test = new HashMap();
        test.put("a", "1");
        test.put("b", "2");
        log.info(JSON.toJSONString(test));

        AjaxResult testAjaxResult = AjaxResult.success(Arrays.asList(1,2,3));
        log.info(JSON.toJSONString(testAjaxResult));
    }
}
