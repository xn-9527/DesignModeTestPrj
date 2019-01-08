package com.my.helloworld;

/**
 * ---------------------
 * 作者：vbirdbest
 * 来源：CSDN
 * 原文：https://blog.csdn.net/vbirdbest/article/details/79863883
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 * Created by xiaoni on 2019/1/8.
 */
public class PersonService {

    private PersonProperties properties;

    public PersonService() {
    }

    public PersonService(PersonProperties properties) {
        this.properties = properties;
    }

    public void sayHello() {
        System.out.println("大家好，我叫: " + properties.getName() + ", 今年" + properties.getAge() + "岁"
                + ", 性别: " + properties.getSex());
    }
}

