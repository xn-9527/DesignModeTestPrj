package cn.test.equals;

/**
 * Created by xiaoni on 2018/9/8.
 */
public class TestEquals {
    public static void main(String[] args) {
        String a = new String("ab"); // a 为一个引用
        String b = new String("ab"); // b为另一个引用,对象的内容一样
        String aa = "ab"; // 放在常量池中
        String bb = "ab"; // 从常量池中查找
        if (aa == bb) {// true
            System.out.println("aa==bb");
        }
        if (a == b) {// false，非同一对象
            System.out.println("a==b");
        }
        if (a.equals(b)) {// true
            System.out.println("aEQb");
        }
        if (42 == 42.0) { // true
            System.out.println("true");
        }

        Integer a1 =1;
        Integer b1 = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);  //true
        System.out.println(e == f);  //false
        System.out.println(c == (a1 + b1)); //true
        System.out.println(c.equals(a1 + b1)); //ture
        System.out.println(g == (a1 + b1)); //true  自动装箱
        /**
         * L.equals(i)执行时，i被转为某种对象类型后，被equals()方法参数obj引用，
         * if (obj instanceof Long)测试失败，所以直接return false;
         */
        System.out.println(g.equals(a1 + b1)); //----false
    }
}
