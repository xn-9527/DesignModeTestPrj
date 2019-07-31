package cn.test.gc;

import cn.test.equals.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Component;

/**
 * @author Created by chay on 2019/7/31.
 * 在JDK8下，Metaspace是完全独立分散的内存结构，由非连续的内存组合起来，
 * 在Metaspace达到了触发GC的阈值的时候(和MaxMetaspaceSize及MetaspaceSize有关)，
 * 就会做一次Full GC，但是这次Full GC，并不会对Metaspace做压缩，唯一卸载类的情况是，
 * 对应的类加载器必须是死的，如果类加载器都是活的，那肯定不会做卸载的事情了
 *
 *  查看gc及内存情况
 *
 * jstat -gcutil 18528[pid进程id] 1000[刷新间隔，单位ms]
 *  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT
    0.00   0.00   0.00  49.39  94.60  89.82 121326  114.750     2    0.005  114.755
 */
@Component
@Slf4j
public class UserFactory {
    private static final BeanCopier beanCopier = BeanCopier.create(User.class, User.class, false);

    public static User copyBean(User sourceBean) {
        try {
            User targetBean = new User();
            beanCopier.copy(sourceBean, targetBean, null);
            return targetBean;
        } catch (Exception e) {
            log.error("MapPoint copyBean error", e);
            return null;
        }
    }

    public static void main(String[] args) {
        User orgin = new User("orgin", "male", "asdfsdf");
        while (true) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info(JSON.toJSONString(copyBean(orgin)));
            copyBean(orgin);
        }
    }
}
