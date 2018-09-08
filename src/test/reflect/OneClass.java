package test.reflect;

/**
 * Created by xiaoni on 2018/9/8.
 */
public class OneClass {

    String name;
    Integer age;

    public void printInfo(){
        System.out.println("打印学生信息");
    }

    public void printAddress(String address){
        System.out.println("hellow,I come from "+address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "OneClass{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
