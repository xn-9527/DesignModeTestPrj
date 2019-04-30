package cn.test.clone.deep.serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
}
