package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiaoni on 2017/3/15.
 * 阿里java开发手册测试
 */
public class TestAlibabaJava {

    public static void main(String[] args) {
        //subList-----------------------------------------------
        System.out.println("subList----------------------------------");
        List<String> parentList = new ArrayList<String>();

        for(int i = 0; i < 5; i++){
            parentList.add(String.valueOf(i));
        }

        List<String> subList = parentList.subList(1, 3);
        for(String s : subList){
            System.out.println(s);//output: 1, 2
        }

        //non-structural modification by sublist, reflect parentList
        subList.set(0, "new 1");
        for(String s : parentList){
            System.out.println(s);//output: 0, new 1, 2, 3, 4
        }

        //如果发生结构性修改的是返回的子list，那么原来的list的大小也会发生变化；
        //structural modification by sublist, reflect parentList
        subList.add(String.valueOf(2.5));
        for(String s : parentList){
            System.out.println(s);//output:0, new 1, 2,    2.5, 3,    4
        }

        //non-structural modification by parentList, reflect sublist
        parentList.set(2, "new 2");
        for(String s : subList){
            System.out.println(s);//output: new 1, new 2
        }

        //而如果发生结构性修改的是原来的list（不包括由于返回的子list导致的改变），那么返回的子list语义上将会是undefined。
        // 在AbstractList（ArrayList的父类）中，undefined的具体表现形式是抛出一个ConcurrentModificationException。
        //structural modification by parentList, sublist becomes undefined(throw exception)
        parentList.add("123");
//        for(String s : subList){
//            System.out.println(s);
//        }
//        subList.get(0);

        //toArray-------------------------------------------------------------------------
        /*
        * 使用集合转数组的方法，必须使用集合的 toArray(T[] array)，传入的是类型完全
        一样的数组，大小就是 list.size()。
        * 使用 toArray 带参方法，入参分配的数组空间不够大时， toArray 方法内部将重新分配
        内存空间，并返回新数组地址； 如果数组元素大于实际所需，下标为[ list.size() ]的数组
        元素将被置为 null，其它数组元素保持原值，因此最好将方法入参数组大小定义与集合元素
        个数一致。
        * */
        List<String> list = new ArrayList<String>(2);
        list.add("guan");
        list.add("bao");
        String[] array = new String[list.size()];
        array = list.toArray(array);
        System.out.println("toArray----------------------------------");
        for(String s : array){
            System.out.println(s);//output:
        }

        //Arrays.asList()----------------------------------------------------------
        /*
        *  使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方
        法，它的 add/remove/clear 方法会抛出 UnsupportedOperationException 异常
        * */
        System.out.println("Arrays.asList()----------------------------------");
        String[] str = new String[] { "a", "b" };
        List list1 = Arrays.asList(str);
        try {
            list1.add("c"); //运行时异常
        } catch (Exception e) {
            e.printStackTrace();
        }
        str[0]= "gujin";
        System.out.println(list1.get(0));//会随着str0修改

        //list remove/add--------------------------------------------------
        /*
        * 不要在 foreach 循环里进行元素的 remove/add 操作。 remove 元素请使用 Iterator
        方式，如果并发操作，需要对 Iterator 对象加锁
        * */
        //演示会报错的情况，正确的做法是用iterator
        System.out.println("list remove/add----------------------------------");
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            if("1".equals(temp)){
                a.remove(temp);
            }
        }
        System.out.println(a);

        try {
            List<String> a1 = new ArrayList<String>();
            a1.add("1");
            a1.add("2");
            for (String temp : a1) {
                if("2".equals(temp)){
                    a1.remove(temp);
                }
            }
            System.out.println(a1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Comparator------------------------------------------------
        System.out.println("Comparator----------------------------------");

        List<Student> aa = new ArrayList<Student>(2);
        aa.add(new Student(3,"abc"));
        aa.add(new Student(1, "bcd"));
        aa.add(new Student(1, "efg"));
        aa.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getId() > o2.getId() ? 1 : -1;
            }
        });
        System.out.println(aa);

        //遍历 Map------------------------------------------------
        // 使用 entrySet 遍历 Map 类集合 KV，而不是 keySet 方式进行遍历
        System.out.println("遍历 Map----------------------------------");
        Map map = new HashMap<>();
        map.put("1", "abc");
        map.put("2", "efg");
        Set mapK = map.keySet();
        System.out.println(mapK);
        Set mapKV = map.entrySet();
        System.out.println(mapKV);

        //SimpleDateFormat------------------------------------------------
        System.out.println("SimpleDateFormat----------------------------------");
        //SimpleDateFormat 是线程不安全的类，一般不要定义为 static 变量，如果定义为
        //static，必须加锁，或者使用 DateUtils 工具类
        System.out.println(df.get().format(new Date()));

        //ThreadLocalRandom------------------------------------------------
        //  避免 Random 实例被多线程使用，虽然共享该实例是线程安全的，但会因竞争同一
        //seed 导致的性能下降。在 JDK7 之前，可以做到每个线程一个实例。
        System.out.println("ThreadLocalRandom----------------------------------");
        Random r = new Random();
        double u1 = r.nextDouble();
        System.out.println(u1);
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        double u = tlr.nextDouble();
        System.out.println(u);
        int rui = tlr.nextInt();
        System.out.println(rui);
        Random rand = new Random();
        int randNum = rand.nextInt(3);
        System.out.println(randNum);

        //AtomicInteger------------------------------------------------
        //  volatile 解决多线程内存不可见问题。对于一写多读，是可以解决变量同步问题，
        //但是如果多写，同样无法解决线程安全问题。如果是 count++操作，使用如下类实现.
        System.out.println("AtomicInteger----------------------------------");
        AtomicInteger count = new AtomicInteger();
        count.addAndGet(1);


        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");


        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

        //------------------------------------------------
        System.out.println("Arrays.asList()----------------------------------");

    }

    //DateUtils
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
