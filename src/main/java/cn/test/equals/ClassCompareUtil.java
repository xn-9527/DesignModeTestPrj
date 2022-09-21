package cn.test.equals;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对比类的属性是否相同，map和list属性不比较
 * @author
 */
public class ClassCompareUtil {
    private final static Logger logger = LoggerFactory.getLogger(ClassCompareUtil.class);

    /**
     * 输出比较结果
     *
     * @param oldObject
     * @param newObject
     * @return 如果返回null，说明两个对象相同；否则，返回输入对象string；
     */
    public static String compareObjectResultString(Object oldObject, Object newObject) {
        String oldValueString = JSON.toJSONString(oldObject);
        String newValueString = JSON.toJSONString(newObject);
        return oldValueString.equals(newValueString) ? null : "oldValue:" + oldValueString + ",newValue:" + newValueString;
    }

    /**
     * 比较两个实体属性值，返回一个boolean,true则表时两个对象中的属性值无差异
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @param isIgnoreBlankString 是否忽略空值空值和null
     * @return 属性差异比较结果boolean
     */
    public static boolean compareObject(Object oldObject, Object newObject, boolean isIgnoreBlankString) {
        String diffString = compareObjectResultString(oldObject, newObject, isIgnoreBlankString);
        return diffString == null;
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @param isIgnoreBlankString 是否忽略空值空值和null
     * @return 属性差异比较结果map
     */
    @SuppressWarnings("rawtypes")
    private static Map<String, Map<String, Object>> compareFields(Object oldObject, Object newObject, boolean isIgnoreBlankString) {
        Map<String, Map<String, Object>> map = null;

        try {
            /**
             * 只有两个对象都是同一类型的才有可比性
             */
            if (oldObject.getClass() == newObject.getClass()) {

                Class clazz = oldObject.getClass();
                //获取object的所有属性
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
                if (pds == null) {
                    return null;
                }
                map = new HashMap<String, Map<String, Object>>(pds.length);
                for (PropertyDescriptor pd : pds) {
                    //遍历获取属性名
                    String name = pd.getName();

                    //获取属性的get方法
                    Method readMethod = pd.getReadMethod();

                    // 在oldObject上调用get方法等同于获得oldObject的属性值
                    Object oldValue = readMethod.invoke(oldObject);
                    // 在newObject上调用get方法等同于获得newObject的属性值
                    Object newValue = readMethod.invoke(newObject);

                    if (isSameProperty(oldValue, newValue, isIgnoreBlankString)) {
                        continue;
                    }

                    // 比较这两个值是否相等,不等就可以放入map了
                    Map<String, Object> valueMap = new HashMap<String, Object>(2);
                    valueMap.put("oldValue", oldValue);
                    valueMap.put("newValue", newValue);

                    map.put(name, valueMap);
                }
            }
        } catch (Exception e) {
            logger.warn("compareFields error", e);
        }
        return map;
    }

    /**
     * 属性是否相同
     *
     * @param sourceProperty
     * @param targetProperty
     * @param isIgnoreBlankString 是否忽略空值空值和null
     * @return
     */
    private static boolean isSameProperty(Object sourceProperty, Object targetProperty, boolean isIgnoreBlankString) {
        if (sourceProperty == null && targetProperty == null) {
            return true;
        }
        if (sourceProperty == null) {
            if (isIgnoreBlankString) {
                //都是空值或者空字符串，等价
                return isBlankString(targetProperty);
            }
            //否则认为不同
            return false;
        }
        if (targetProperty == null) {
            if (isIgnoreBlankString) {
                //都是空值或者空字符串，等价
                return isBlankString(sourceProperty);
            }
            //否则认为不同
            return false;
        }
        if (!sourceProperty.getClass().equals(targetProperty.getClass())) {
            //不同类型认为不同
            return false;
        }
        if (sourceProperty instanceof List) {
            //list简单用json比较，fastjson没有顺序，可能不对
            return JSON.toJSONString(sourceProperty).equals(JSON.toJSONString(targetProperty));
        }
        if (targetProperty instanceof List) {
            //list简单用json比较，fastjson没有顺序，可能不对
            return JSON.toJSONString(targetProperty).equals(JSON.toJSONString(sourceProperty));
        }
        if (isIgnoreBlankString && (sourceProperty instanceof String)) {
            if (StringUtils.isBlank((String)sourceProperty) && StringUtils.isBlank((String)targetProperty)) {
                //都是空值或者空字符串，等价
                return true;
            }
        }
        if (sourceProperty instanceof Timestamp) {
            sourceProperty = new Date(((Timestamp) sourceProperty).getTime());
            targetProperty = new Date(((Timestamp) targetProperty).getTime());
            return sourceProperty.equals(targetProperty);
        }

        return sourceProperty.equals(targetProperty);
    }

    private static boolean isBlankString(Object o) {
        return (o instanceof String && StringUtils.isBlank((String)o));
    }

    /**
     * 精确输出比较结果
     *
     * @param oldObject
     * @param newObject
     * @param isIgnoreBlankString 是否忽略空值空值和null
     * @return 如果返回null，说明两个对象属性没有不同；否则，返回不同的属性值；
     */
    public static String compareObjectResultString(Object oldObject, Object newObject, boolean isIgnoreBlankString) {
        //1. 旧对象为空，新对象也为空，认为相同
        if (oldObject == null && newObject == null) {
            return null;
        }
        //2. 旧对象为空，新对象不为空
        if (oldObject == null) {
            if (isIgnoreBlankString && isBlankString(newObject)) {
                //都是空值或者空字符串，等价
                return null;
            }
            return "oldValue is null, newValue not null";
        }
        //3. 旧对象不为空，新对象为空
        if (newObject == null) {
            if (isIgnoreBlankString && isBlankString(oldObject)) {
                //都是空值或者空字符串，等价
                return null;
            }
            return "oldValue not null, newValue is null";
        }
        //4. 旧对象不为空，新对象不为空
        Map<String, Map<String, Object>> resultMap = compareFields(oldObject, newObject, isIgnoreBlankString);
        if (null == resultMap) {
            return null;
        }
        int size = resultMap.size();
        if (size > 0) {
            StringBuilder sb = new StringBuilder();
            for (String key : resultMap.keySet()) {
                sb.append(key).append("(oldValue:").append(resultMap.get(key).get("oldValue")).append(",newValue:")
                        .append(resultMap.get(key).get("newValue")).append(");");
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        User user1 = new User(null,"F","213");
        User user2 = new User("","F","213");
        System.out.println("compare ignore blank string result:" + compareObjectResultString(user1, user2, true));
        System.out.println("compare not ignore blank string result:" + compareObjectResultString(user1, user2, false));
        System.out.println("compare as jsonString result:" + compareObjectResultString(user1, user2));
    }
}

