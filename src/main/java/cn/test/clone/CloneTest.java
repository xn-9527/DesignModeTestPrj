package cn.test.clone;

import cn.test.clone.deep.cloneable.Professor;
import cn.test.clone.deep.cloneable.Student;
import cn.test.clone.deep.serializable.Professor2;
import cn.test.clone.deep.serializable.Student2;
import cn.test.clone.notdeep.Professor0;
import cn.test.clone.notdeep.Student0;

import java.io.IOException;

/**
 * Created by xiaoni on 2018/12/19.
 *
 * 深拷贝浅拷贝本质区别：
 * 浅拷贝：对象内部某些属性不会拷贝，只会使用原引用。比如类、接口、数组、枚举等。
 * 深拷贝：对象内部的所以属性都是副本。
 */
public class CloneTest {
    public static void main(String[] args) {
        System.out.println("test notDeepClone cloneable=====================");
        Professor0 p = new Professor0("wangwu", 50);
        Student0 s1 = new Student0("zhangsan", 18, p);
        Student0 s2 = (Student0) s1.clone();
        s2.p.name = "lisi";
        s2.p.age = 30;
        s2.name = "z";
        s2.age = 45;
        System.out.println("学生s1的姓名：" + s1.name + ",\n学生s1的年纪：" + s1.age + ",\n学生s1教授的姓名：" + s1.p.name + ",\n学生s1教授的年纪:" + s1.p.age);
        System.out.println("学生s2的姓名：" + s2.name + ",\n学生s2的年纪：" + s2.age + ",\n学生s2教授的姓名：" + s2.p.name + ",\n学生s2教授的年纪:" + s2.p.age);

        System.out.println("test deepClone cloneable=====================");
        Professor p1 = new Professor("wangwu", 50);
        Student s3 = new Student("zhangsan", 18, p1);
        Student s4 = (Student) s3.clone();
        s4.p.name = "lisi";
        s4.p.age = 30;
        s4.name = "z";
        s4.age = 45;
        System.out.println("学生s3的姓名：" + s3.name + ",\n学生s3的年纪：" + s3.age + ",\n学生s3教授的姓名：" + s3.p.name + ",\n学生s3教授的年纪:" + s3.p.age);
        System.out.println("学生s4的姓名：" + s4.name + ",\n学生s4的年纪：" + s4.age + ",\n学生s4教授的姓名：" + s4.p.name + ",\n学生s4教授的年纪:" + s4.p.age);

        System.out.println("test deepClone serializable=====================");
        Professor2 p2 = new Professor2("wangwu", 50);
        Student2 s5 = new Student2("zhangsan", 18, p2);
        Student2 s6 = null;
        try {
            s6 = (Student2) s5.deepClone();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        s6.p.name = "lisi";
        s6.p.age = 30;
        s6.name = "z";
        s6.age = 45;
        System.out.println("学生s5的姓名：" + s5.name + ",\n学生s5的年纪：" + s5.age + ",\n学生s5教授的姓名：" + s5.p.name + ",\n学生s5教授的年纪:" + s5.p.age);
        System.out.println("学生s6的姓名：" + s6.name + ",\n学生s6的年纪：" + s6.age + ",\n学生s6教授的姓名：" + s6.p.name + ",\n学生s6教授的年纪:" + s6.p.age);

    }
}
