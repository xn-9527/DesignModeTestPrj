package cn.test.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

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
            c1 = Class.forName("cn.test.reflect.OneClass");
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


        /**
         * 通过反射调用类中的方法
         */
        try {
            //获取Student类名称为printinfo地方法
            Method methods1=c1.getMethod("printInfo");
            //调用frintInfo方法
            methods1.invoke(c1.newInstance()); //通过实例化的对象，调用无参数的方法
            //获取类中名称为printInfo地方法，String，class是参数类型
            Method methods2=c1.getMethod("printAddress", String.class);//注意参数不是String
            //调用printAddress方法，其中HK是方法传入的参数值
            methods2.invoke(c1.newInstance(),"HK");//通过对象，调用有参数的方法
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
        }

        /**
         * 反射获取类属性类型和值
         */
        try {
            OneClass oneClass = new OneClass();
            oneClass.setAge(123);
            oneClass.setName("hahah");
            getObjectValue(oneClass);
            System.out.println(getMethodName("dsfewrt"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void getObjectValue(Object object) throws Exception {
        //我们项目的所有实体类都继承BaseDomain （所有实体基类：该类只是串行化一下）
        //不需要的自己去掉即可
        if (object != null && object instanceof OneClass) {//if (object!=null )  ----begin
            // 拿到该类
            Class<?> clz = object.getClass();
            // 获取实体类的所有属性，返回Field数组
            Field[] fields = clz.getDeclaredFields();

            for (Field field : fields) {// --for() begin
                System.out.println(field.getGenericType());//打印该类的所有属性类型

                // 如果类型是String
                if (field.getGenericType().toString().equals(
                        "class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    // 拿到该属性的gettet方法
                    /**
                     * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
                     * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
                     * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
                     */
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));

                    String val = (String) m.invoke(object);// 调用getter方法获取属性值
                    if (val != null) {
                        System.out.println("String type:" + val);
                    }

                }

                // 如果类型是Integer
                else if (field.getGenericType().toString().equals(
                        "class java.lang.Integer")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Integer val = (Integer) m.invoke(object);
                    if (val != null) {
                        System.out.println("Integer type:" + val);
                    }

                }

                // 如果类型是Double
                else if (field.getGenericType().toString().equals(
                        "class java.lang.Double")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Double val = (Double) m.invoke(object);
                    if (val != null) {
                        System.out.println("Double type:" + val);
                    }

                }

                // 如果类型是Boolean 是封装类
                else if (field.getGenericType().toString().equals(
                        "class java.lang.Boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            field.getName());
                    Boolean val = (Boolean) m.invoke(object);
                    if (val != null) {
                        System.out.println("Boolean type:" + val);
                    }

                }

                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名
                else if (field.getGenericType().toString().equals("boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            field.getName());
                    Boolean val = (Boolean) m.invoke(object);
                    if (val != null) {
                        System.out.println("boolean type:" + val);
                    }

                }
                // 如果类型是Date
                else if (field.getGenericType().toString().equals(
                        "class java.util.Date")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Date val = (Date) m.invoke(object);
                    if (val != null) {
                        System.out.println("Date type:" + val);
                    }

                }
                // 如果类型是Short
                else if (field.getGenericType().toString().equals(
                        "class java.lang.Short")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Short val = (Short) m.invoke(object);
                    if (val != null) {
                        System.out.println("Short type:" + val);
                    }

                }
                // 如果还需要其他的类型请自己做扩展

            }//for() --end

        }//if (object!=null )  ----end
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
