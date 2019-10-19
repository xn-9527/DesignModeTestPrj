package cn.test.clone.deep.serializable;

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
    public synchronized static <T extends Serializable> T deepClone(T obj) {
        T clonedObj = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);
            clonedObj = (T) ois.readObject();
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

    /**
     * 深克隆map
     *
     * @param source
     * @param tClass map的Value的类型
     * @return
     */
    public synchronized static Map deepCloneMap(Map source, Class tClass) {
        if (source == null) {
            return null;
        }
        Map returnMapTemp = JSONArray.parseObject(JSON.toJSONString(source), HashMap.class);
        Map returnMap = new HashMap(source.size());
        returnMapTemp.forEach((key, jsonObject) -> {
            returnMap.put(key, JSON.parseObject(JSON.toJSONString(jsonObject), tClass));
        });
        return returnMap;
    }

    /**
     * 深克隆map
     *
     * @param source
     * @param tClass
     * @return
     */
    public synchronized static <T extends Map> T deepCloneMap2(T source, Class tClass) {
        if (source == null) {
            return null;
        }
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
}
