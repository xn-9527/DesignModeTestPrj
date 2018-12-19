package cn.test.clone.deep.serializable;

import java.io.*;

/**
 * Created by xiaoni on 2018/12/19.
 */
public class Student2 implements Serializable {
    /**
     *
     */
    public static final long serialVersionUID = 1L;
    public String name;// 常量对象。
    public int age;
    public Professor2 p;// 学生1和学生2的引用值都是一样的。

    public Student2(String name, int age, Professor2 p) {
        this.name = name;
        this.age = age;
        this.p = p;
    }

    public Object deepClone() throws IOException, ClassNotFoundException {
        // 将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);
        // 从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (oi.readObject());
    }

}
