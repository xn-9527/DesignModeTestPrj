package cn.test.clone.deep.serializable;

import cn.test.equals.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

/**
 * @author Created by chay on 2019/4/30.
 */
@Slf4j
public class MyCloneUtils {
    /**
     * 对象深度克隆---使用序列化进行深拷贝
     *
     * @param obj 要克隆的对象
     * @return 注意：
     * 使用序列化的方式来实现对象的深拷贝，但是前提是，对象必须是实现了 Serializable接口才可以，Map本身没有实现
     * Serializable 这个接口，所以这种方式不能序列化Map，也就是不能深拷贝Map。但是HashMap是可以的，因为它实现了Serializable。
     */
    public static <T extends Serializable> T deepClone(T obj) {
        T clonedObj = null;
        ObjectInputStream ois = null;
        try {
            synchronized(obj) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                oos.close();
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ois = new ObjectInputStream(bais);
                clonedObj = (T) ois.readObject();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return clonedObj;
    }

    public static <T> T deepClone2(T obj) {
        return JSON.parseObject(JSON.toJSONString(obj), (Class<T>) obj.getClass());
    }

    /**
     * 深克隆map
     *
     * @param source
     * @param tClass map的Value的类型
     * @return
     */
    public static Map deepCloneMap(Map source, Class tClass) {
        if (source == null) {
            return null;
        }
        synchronized (source) {
            Map returnMapTemp = JSONArray.parseObject(JSON.toJSONString(source), HashMap.class);
            Map returnMap = new HashMap(source.size());
            returnMapTemp.forEach((key, jsonObject) -> {
                returnMap.put(key, JSON.parseObject(JSON.toJSONString(jsonObject), tClass));
            });
            return returnMap;
        }
    }

    /**
     * 深克隆map
     *
     * @param source
     * @param tClass
     * @return
     */
    public static <T extends Map> T deepCloneMap2(T source, Class tClass) {
        if (source == null) {
            return null;
        }
        synchronized (source) {
            Class<T> sourceClazz = (Class<T>) source.getClass();
            T returnMapTemp = JSONArray.parseObject(JSON.toJSONString(source), sourceClazz);
            T returnMap = null;
            try {
                T finalReturnMap = sourceClazz.newInstance();
                returnMapTemp.forEach((key, jsonObject) -> {
                    finalReturnMap.put(key, JSON.parseObject(JSON.toJSONString(jsonObject), tClass));
                });
                returnMap = finalReturnMap;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return returnMap;
        }
    }

    public static void main(String[] args) {
        //hashMap是无序的，所以输出结果a3在a0前面
        HashMap a = new HashMap();
        a.put("a1", 1);
        a.put("a2", 2);
        a.put("a0", 0);
        a.put("a3", 3);
        log.info("a={}");
        MyCloneUtils.printMapValueSet(MyCloneUtils.deepCloneMap2(a, Integer.class).entrySet());
        a.put("a1", 3);
        log.info("after change a1, a={}");
        MyCloneUtils.printMapValueSet(MyCloneUtils.deepCloneMap2(a, Integer.class).entrySet());

        //LinkedHashMap是有序的，有插入顺序和访问顺序两种，默认访问顺序是关闭的
        LinkedHashMap b = new LinkedHashMap();
        b.put("b1", 1);
        b.put("b2", 2);
        b.put("b0", 0);
        b.put("b3", 3);
        log.info("b={}");
        MyCloneUtils.printMapValueSet(MyCloneUtils.deepCloneMap2(b, Integer.class).entrySet());
        b.put("b1", 3);
        log.info("after change b1, b={}");
        MyCloneUtils.printMapValueSet(MyCloneUtils.deepCloneMap2(b, Integer.class).entrySet());

        //启用LinkedHashMap访问顺序,最近被get的元素排在尾部
        LinkedHashMap c = new LinkedHashMap(10, 0.75f,true);
        c.put("c1", 1);
        c.put("c2", 2);
        c.put("c0", 0);
        c.put("c3", 3);
        log.info("c={}");
        MyCloneUtils.printMapValueSet(MyCloneUtils.deepCloneMap2(c, Integer.class).entrySet());
        c.get("c1");
        log.info("after get c1, c={}");
        MyCloneUtils.printMapValueSet(MyCloneUtils.deepCloneMap2(c, Integer.class).entrySet());

        try {
            //测试一个线程在克隆，另一个线程修改被克隆的对象
            LinkedHashMap d = new LinkedHashMap();
            for (int i = 0; i < 1000; i++) {
                d.put("d" + i, i);
            }
            log.info("##############d before");
            MyCloneUtils.printMapValueSet(MyCloneUtils.deepCloneMap2(d, Integer.class).entrySet());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 2000; i < 3000; i++) {
                        d.put("d" + i, i);
                    }
                    log.info("##############d after in thread2");
                    /**
                     * designMode 2019-10-22 14:13:51.047 INFO  cn.test.clone.deep.serializable.MyCloneUtils Line:148 - ##############after
                     designMode 2019-10-22 14:13:51.048 INFO  cn.test.clone.deep.serializable.MyCloneUtils Line:143 - ##############after in thread2
                     Exception in thread "main" java.util.ConcurrentModificationException
                     at java.util.LinkedHashMap$LinkedHashIterator.nextNode(LinkedHashMap.java:711)
                     at java.util.LinkedHashMap$LinkedEntryIterator.next(LinkedHashMap.java:744)
                     at java.util.LinkedHashMap$LinkedEntryIterator.next(LinkedHashMap.java:742)
                     at com.alibaba.fastjson.serializer.MapSerializer.write(MapSerializer.java:85)
                     at com.alibaba.fastjson.serializer.JSONSerializer.write(JSONSerializer.java:264)
                     at com.alibaba.fastjson.JSON.toJSONString(JSON.java:637)
                     at com.alibaba.fastjson.JSON.toJSONString(JSON.java:579)
                     at com.alibaba.fastjson.JSON.toJSONString(JSON.java:544)
                     at cn.test.clone.deep.serializable.MyCloneUtils.deepCloneMap2(MyCloneUtils.java:79)
                     at cn.test.clone.deep.serializable.MyCloneUtils.main(MyCloneUtils.java:149)

                     没有打印thread1 finished，说明LinkedHashMap非线程安全，在线程1的打印过程中，线程2的插入操作影响了线程1的打印

                     test step2:当在Clone方法里面加上同步后，仍然报这个错误，是因为我们的打印方法没有保护，是打印方法抛的异常
                     */
                    MyCloneUtils.printMapValueSet(MyCloneUtils.deepCloneMap2(d, Integer.class).entrySet());
                    log.info("d thread2 finished");
                }
            }).start();
            log.info("##############d after");
            MyCloneUtils.printMapValueSet(MyCloneUtils.deepCloneMap2(d, Integer.class).entrySet());
            log.info("d thread1 finished");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        try {
            //尝试使用同步方法打印，看是否还会报错
            LinkedHashMap e = new LinkedHashMap();
            for (int i = 0; i < 1000; i++) {
                e.put("e" + i, i);
            }
            log.info("##############e before");
            MyCloneUtils.syncPrintMapValueSet(MyCloneUtils.deepCloneMap2(e, Integer.class).entrySet());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 2000; i < 3000; i++) {
                        e.put("e" + i, i);
                    }
                    log.info("##############e after in thread2");
                    MyCloneUtils.syncPrintMapValueSet(MyCloneUtils.deepCloneMap2(e, Integer.class).entrySet());
                    log.info("e thread2 finished");
                }
            }).start();
            log.info("##############e after");
            /**
             * 在这里抛异常了，因为clone过程中，还是需要遍历源，而clone方法里面没有锁定synchronize(source)。
             *
             * java.util.ConcurrentModificationException: null
             at java.util.LinkedHashMap$LinkedHashIterator.nextNode(LinkedHashMap.java:711)
             at java.util.LinkedHashMap$LinkedEntryIterator.next(LinkedHashMap.java:744)
             at java.util.LinkedHashMap$LinkedEntryIterator.next(LinkedHashMap.java:742)
             at com.alibaba.fastjson.serializer.MapSerializer.write(MapSerializer.java:85)
             at com.alibaba.fastjson.serializer.JSONSerializer.write(JSONSerializer.java:264)
             at com.alibaba.fastjson.JSON.toJSONString(JSON.java:637)
             at com.alibaba.fastjson.JSON.toJSONString(JSON.java:579)
             at com.alibaba.fastjson.JSON.toJSONString(JSON.java:544)
             at cn.test.clone.deep.serializable.MyCloneUtils.deepCloneMap2(MyCloneUtils.java:84)
             at cn.test.clone.deep.serializable.MyCloneUtils.main(MyCloneUtils.java:199)

             test step2：当我们在clone方法里面加上保护后，打印方法也有保护，就不再抛异常了
             */
            MyCloneUtils.syncPrintMapValueSet(MyCloneUtils.deepCloneMap2(e, Integer.class).entrySet());
            log.info("e thread1 finished");
        } catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        }

        System.out.println("################################");
        User user = new User("test", "mail", "123");
        User userClone = MyCloneUtils.deepClone2(user);
        log.info(JSON.toJSONString(userClone));
        log.info(String.valueOf(user == userClone));
    }

    public static void printMapValueSet(Set set) {
        Iterator<Map.Entry<String, String>> iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            String key = (String) entry.getKey();
            String value = String.valueOf(entry.getValue());
            System.out.println("key:" + key + ",value:" + value);
        }
    }

    public static void syncPrintMapValueSet(Set set) {
        synchronized (set) {
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                String key = (String) entry.getKey();
                String value = String.valueOf(entry.getValue());
                System.out.println("key:" + key + ",value:" + value);
            }
        }
    }
}
