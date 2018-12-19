package cn.test.clone.deep.cloneable;

/**
 * Created by xiaoni on 2018/12/19.
 */
public class Student implements Cloneable {
    public String name;
    public int age;
    public Professor p;

    public Student(String name, int age, Professor p) {
        this.name = name;
        this.age = age;
        this.p = p;
    }

    @Override
    public Object clone() {
        Student o = null;
        try {
            o = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        //深拷贝对象内部对象，也需要拷贝个新对象
        o.p = (Professor) p.clone();
        return o;
    }
}
