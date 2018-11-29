package cn.test.guava.testMultimap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xiaoni on 2018/11/29.
 */
public class TestMultimap {
    /*
        //传统的场景:  Map<String,List<MyClass>> map = new HashMap<String,List<MyClass>>();
        //缺点:向map里面添加元素不太方便,需要这样实现
        //补充: HashMap的key和value皆可为null,HashTable的key和value皆不可为null. 二者的key皆不可重复,若重复,后添加的会覆盖之前的.

    void putMyObject(String key, Object value) {
        List<Object> myClassList = myClassListMap.get(key);
        if (myClassList == null) {
            myClassList = new ArrayList<Object>();
            myClassListMap.put(key, myClassList);
        }
        myClassList.add(value);
    }*/

    public static void main(String[] args) {
        //上面传统的场景,可以使用ArrayListMultimap
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("fruit", "bannana");
        multimap.put("fruit", "apple");//key可以重复
        multimap.put("fruit", "apple");//value可以重复,不会覆盖之前的
        multimap.put("fruit", "peach");
        multimap.put("fish", "crucian");//欧洲鲫鱼
        multimap.put("fish", "carp");//鲤鱼

        System.err.println(multimap.size());//6

        Collection<String> fruits = multimap.get("fruit");
        System.err.println(fruits);//[bannana, apple, apple, peach]

        for (String s : multimap.values()) {
            System.err.print(s + " , ");//bannana , apple , apple , peach , crucian , carp ,
        }

        multimap.remove("fruit", "apple");
        System.err.println(fruits);//[bannana, apple, peach]   注意:这里只remove了一个apple,因此还有一个apple

        multimap.removeAll("fruit");
        System.err.println(fruits);//[]

        /**
         如果将multimap 直接返回前端, 返回的数据是 {
         "empty":false
         } ,是否可以返回给前端, Lifu不清楚
         */

        //get(key) 返回的是collection,如果希望返回的是list,可以选择ListMultimap来接收create()的返回值

        ListMultimap<String, String> listMultimap = ArrayListMultimap.create();
        listMultimap.put("fruit", "bannana");
        listMultimap.put("fruit", "apple");
        listMultimap.put("fruit", "peach");
        listMultimap.put("fish", "crucian");//欧洲鲫鱼
        listMultimap.put("fish", "carp");//鲤鱼

        List<String> fruits1 = listMultimap.get("fruit");
        System.err.println(fruits1);//[bannana, apple, peach]


        //对比 HashMultimap-------------------------------------

        Multimap<String, String> multimap1 = HashMultimap.create();
        multimap1.put("fruit", "bannana");
        multimap1.put("fruit", "apple");
        multimap1.put("fruit", "apple");

        System.err.println(multimap1.size());//2
        System.err.println(multimap1.get("fruit"));//[apple, bannana]     注意: 这里只有一个apple
    }


}
