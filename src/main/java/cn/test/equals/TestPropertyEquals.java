package cn.test.equals;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: x
 * @Date: 2022/9/20 10:05 下午
 * @Description:
 */
public class TestPropertyEquals {
    /**
     * 属性是否相同，规避空值和null
     *
     * @param property
     * @return
     */
    public static boolean isSameProperty(Object sourceProperty, Object targetProperty) {
        if (sourceProperty == null && targetProperty == null) {
            return true;
        }
        if (sourceProperty == null) {
            //都是空值或者空字符串，等价
            return isBlankString(targetProperty);
        }
        if (targetProperty == null) {
            //都是空值或者空字符串，等价
            return isBlankString(sourceProperty);
        }
        if (!sourceProperty.getClass().equals(targetProperty.getClass())) {
            return false;
        }
        if (sourceProperty instanceof String) {
            if (StringUtils.isBlank((String)sourceProperty) && StringUtils.isBlank((String)targetProperty)) {
                //都是空值或者空字符串，等价
                return true;
            }
        }
        return sourceProperty.equals(targetProperty);
    }

    private static boolean isBlankString(Object o) {
        return (o instanceof String && StringUtils.isBlank((String)o));
    }

    public static void main(String[] args) {
        String a1 = null;
        String a2 = "";

        System.out.println("isSameEmptyString: " + isSameProperty(a1, a2));
    }
}
