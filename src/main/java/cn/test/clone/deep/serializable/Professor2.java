package cn.test.clone.deep.serializable;

import java.io.Serializable;

/**
 * Created by xiaoni on 2018/12/19.
 * <p>
 * 如果这个子类不可序列化，会报错
 * java.io.NotSerializableException: cn.test.clone.deep.serializable.Professor2
 * at java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1184)
 * at java.io.ObjectOutputStream.defaultWriteFields(ObjectOutputStream.java:1548)
 * at java.io.ObjectOutputStream.writeSerialData(ObjectOutputStream.java:1509)
 * at java.io.ObjectOutputStream.writeOrdinaryObject(ObjectOutputStream.java:1432)
 * at java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1178)
 * at java.io.ObjectOutputStream.writeObject(ObjectOutputStream.java:348)
 * at cn.test.clone.deep.serializable.Student2.deepClone(Student2.java:27)
 * at cn.test.clone.CloneTest.main(CloneTest.java:48)
 * Exception in thread "main" java.lang.NullPointerException
 * at cn.test.clone.CloneTest.main(CloneTest.java:54)
 */
public class Professor2 implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String name;
    public int age;

    public Professor2(String name, int age) {
        this.name = name;
        this.age = age;
    }
}