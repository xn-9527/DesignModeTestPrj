package com.my.helloworld;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ---------------------
 * 作者：vbirdbest
 * 来源：CSDN
 * 原文：https://blog.csdn.net/vbirdbest/article/details/79863883
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 * Created by xiaoni on 2019/1/8.
 */
@ConfigurationProperties(prefix = "spring.person")
public class PersonProperties {
    // 姓名
    private String name;
    // 年龄
    private int age;
    // 性别
    private String sex = "M";

    // Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

