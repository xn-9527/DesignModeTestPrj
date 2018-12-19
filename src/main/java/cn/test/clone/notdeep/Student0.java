package cn.test.clone.notdeep;

/**
 * Created by xiaoni on 2018/12/19.
 */
public class Student0 implements Cloneable {
    public String name;// 常量对象。
    public int age;
    public Professor0 p;// 学生1和学生2的引用值都是一样的。

    public Student0(String name, int age, Professor0 p) {
        this.name = name;
        this.age = age;
        this.p = p;
    }

    @Override
    public Object clone() {
        Student0 o = null;
        try {
            o = (Student0) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }

        return o;
    }
}
