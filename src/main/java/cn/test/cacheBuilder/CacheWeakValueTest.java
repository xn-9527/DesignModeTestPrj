package cn.test.cacheBuilder;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoni on 2019/11/13.
 */
@Slf4j
public class CacheWeakValueTest {
    private final Cache<String, String> messageCache = CacheBuilder.newBuilder()
            //过期时间未访问后X秒
            .expireAfterAccess(30, TimeUnit.SECONDS)
            //值弱引用，可及时被垃圾回收
            .weakValues()
            //todo debug用，稳定后删除
            .recordStats()
            .build();

    /**
     * 放入一条消息，然后强制垃圾回收，再看消息是否还存在
     */
    public void test() {
        messageCache.put("key", "testsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsf");
        for (int i = 0; i < 70; i++) {
            messageCache.put("key" + i, "testtestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsftestsdfasdgfdsafsdfdfdsf" + i);

            log.info("loop {} : size {}", i, messageCache.size());

            if (i == 10) {
                log.info("gc started");
                System.gc();

                log.info("gc finished");
            }

            if (i == 30) {
                log.info("30 reached!");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public static void main(String[] args) {
        CacheWeakValueTest cacheWeakValueTest = new CacheWeakValueTest();
        cacheWeakValueTest.test();
    }
}
