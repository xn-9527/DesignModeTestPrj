package test.reflect;

/**
 * Created by xiaoni on 2018/9/8.
 *
 * 有一个叫Class的类，它是反射的源头。

 正常方式：通过完整的类名—>通过new实例化—>取得实例化对象
 反射方式：实例化对象—>getClass()方法—>通过完整的类名
 */
public class TestReflect {
    public static void main(String[] args) {
        Class<?> c1 = null;
        Class<?> c2 = null;
        Class<?> c3 = null;
        try
        {
            // 方法一：forName(重要)
            c1 = Class.forName("test.reflect.OneClass");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        // 方法二
        c2 = new OneClass().getClass();

        // 方法三
        c3 = OneClass.class;
        System.out.println(c1.getName());
        System.out.println(c2.getName());
        System.out.println(c3.getName());
    }
}
