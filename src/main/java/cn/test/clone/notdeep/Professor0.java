package cn.test.clone.notdeep;

public class Professor0 implements Cloneable {
    public String name;
    public int age;

    public Professor0(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}