package cn.chay.demo.service.impl;

import cn.chay.demo.service.IDemoService;
import cn.chay.framework.annotation.GPService;

/**
 * Created by xiaoni on 2019/10/13.
 */
@GPService
public class DemoServiceImpl implements IDemoService{
    @Override
    public String get(String name) {
        return "This is chay demo, welcome name:" + name;
    }
}
