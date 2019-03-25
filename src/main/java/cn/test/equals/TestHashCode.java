package cn.test.equals;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoni on 2019/3/25.
 *
 * 结论：
 * MapPoint和User复写的equals里面只有一个区别会导致两个属性相同的对象euqals和hashcode不同：
 * if (!super.equals(o)) {
 *    log.info("this父类的hashCode方法：" + super.hashCode());
 *    log.info("比较对象o的hashCode：" + o.hashCode());
 *    log.info("父类的equls匹配失败");
 *    return false;
 * }
 * 原生的equals没有这个比较，而MapPoint增加了这个比较后，结果就会正确，不同的对象也能区分开了。
 */
@Slf4j
public class TestHashCode {

    public static void main(String[] args) {
        log.info("####################测试复写User的HashCode和equals方法");
        User a = new User("a", "female", "a1234");
        User b = new User("b", "male", "b234");

        log.info("a的hashCode：" + a.hashCode());
        log.info("b的hashCode：" + b.hashCode());
        log.info("a和b的equals：" + a.equals(b));//false
        log.info("a和b的==：" + (a == b));//false

        User c = new User("c", "male", "c1234");
        User d = new User("c", "male", "c1234");
        BeanUtils.copyProperties(a, c);
        BeanUtils.copyProperties(a, d);
        log.info("c的hashCode：" + c.hashCode());
        log.info("d的hashCode：" + d.hashCode());
        log.info("c和d的equals：" + c.equals(d));//true
        log.info("c和d的==：" + (c == d));//false

        //a和b的hashcode不同，不会替换
        //c和d的hashCode相同，会替换
        Map aM = new HashMap();
        aM.put(a, 1);
        aM.put(b, 2);
        aM.put(c, 3);
        aM.put(d, 4);
        log.info(aM.toString());

        log.info("####################测试不复写User的HashCode和equals方法");
        User2 a2 = new User2("a", "female", "a1234");
        User2 b2 = new User2("b", "male", "b234");

        log.info("a2的hashCode：" + a2.hashCode());
        log.info("b2的hashCode：" + b2.hashCode());
        log.info("a2和b2的equals：" + a2.equals(b2));//false
        log.info("a2和b2的==：" + (a2 == b2));//false

        User2 c2 = new User2("c", "male", "c1234");
        User2 d2 = new User2("c", "male", "c1234");
        BeanUtils.copyProperties(a2, c2);
        BeanUtils.copyProperties(a2, d2);
        log.info("c2的hashCode：" + c2.hashCode());
        log.info("d2的hashCode：" + d2.hashCode());
        log.info("c2和d2的equals：" + c2.equals(d2));//true
        log.info("c2和d2的==：" + (c2 == d2));//false

        //a和b的hashcode不同，不会替换
        //c和d的hashCode相同，会替换
        Map aM2 = new HashMap();
        aM2.put(a2, 1);
        aM2.put(b2, 2);
        aM2.put(c2, 3);
        aM2.put(d2, 4);
        log.info(aM2.toString());

        log.info("####################测试复写MapPoint的HashCode和equals方法");
        MapPoint orin = new MapPoint("aaa", "x", "y", "a", 1.23D, 2.23D, 3.1D,
                1, "asdf", 1, 1, 2L, 0, 1L);
        MapPoint aMPoint = new MapPoint();
        MapPoint bMPoint = new MapPoint();
        BeanUtils.copyProperties(orin, aMPoint);
        BeanUtils.copyProperties(orin, bMPoint);
        log.info("aMPoint hashCode:" + aMPoint.hashCode());
        log.info("bMPoint hashCode:" + bMPoint.hashCode());
        log.info("aMPoint和bMPoint的equals：" + aMPoint.equals(bMPoint));//false
        log.info("aMPoint和bMPoint的==：" + (aMPoint == bMPoint));//false

        log.info("####################测试不复写MapPoint的HashCode和equals方法");
        MapPoint2 orin2 = new MapPoint2("aaa", "x", "y", "a", 1.23D, 2.23D, 3.1D,
                1, "asdf", 1, 1, 2L, 0, 1L);
        MapPoint2 cMPoint = new MapPoint2();
        MapPoint2 dMPoint = new MapPoint2();
        BeanUtils.copyProperties(orin2, cMPoint);
        BeanUtils.copyProperties(orin2, dMPoint);
        log.info("cMPoint hashCode:" + cMPoint.hashCode());
        log.info("dMPoint hashCode:" + dMPoint.hashCode());
        log.info("cMPoint和dMPoint的equals：" + cMPoint.equals(dMPoint));//true
        log.info("cMPoint和dMPoint的==：" + (cMPoint == dMPoint));//false
    }

}
