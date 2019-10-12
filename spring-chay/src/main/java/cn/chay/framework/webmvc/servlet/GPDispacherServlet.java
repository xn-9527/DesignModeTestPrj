package cn.chay.framework.webmvc.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiaoni on 2019/10/13.
 */
public class GPDispacherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.等待请求
        doDispatch();
    }

    private void doDispatch() {

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //从这里开始启动


        //1.加载配置文件
        doLoadConfig();


        //2.扫描所有相关的类


        //3.初始化所有相关的类


        //4.自动注入


        //==============spring的核心初始化完成==========================

        //5.初始化HandlerMapping



    }

    private void doLoadConfig() {


    }
}
