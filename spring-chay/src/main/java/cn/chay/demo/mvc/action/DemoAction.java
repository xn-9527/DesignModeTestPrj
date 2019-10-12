package cn.chay.demo.mvc.action;

import cn.chay.demo.service.IDemoService;
import cn.chay.framework.annotation.GPAutoWired;
import cn.chay.framework.annotation.GPController;
import cn.chay.framework.annotation.GPRequestMapping;
import cn.chay.framework.annotation.GPRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiaoni on 2019/10/13.
 */
@GPController
@GPRequestMapping("/demo")
public class DemoAction {
    @GPAutoWired
    private IDemoService demoService;

    @GPRequestMapping("/query.json")
    public void query(HttpServletRequest req, HttpServletResponse resp,
                      @GPRequestParam("name") String name ) {
        String result = demoService.get(name);
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
