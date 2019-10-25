package cn.test.cacheBuilder;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoni on 2019/10/25.
 */
@Slf4j
public class CacheBuilderTest {
    private final Cache<String, String> messageCache = CacheBuilder.newBuilder()
            //过期时间未访问后X秒
            .expireAfterAccess(50, TimeUnit.SECONDS)
            //值弱引用，可及时被垃圾回收
            .weakValues()
            //todo debug用，稳定后删除
            .recordStats()
            .build();

    private Cache<String, Integer> cache = CacheBuilder.newBuilder()
            .maximumSize(10)  //最多存放十个数据
            .expireAfterWrite(10, TimeUnit.SECONDS)  //缓存200秒
            .recordStats()   //开启 记录状态数据功能
            .build();

    public void testStats() {
        LoadingCache<String, String> dynamicCache = CacheBuilder.newBuilder()
                //过期时间未访问后X秒
                .expireAfterAccess(50, TimeUnit.SECONDS)
                //值弱引用，可及时被垃圾回收
                .weakValues()
                //todo debug用，稳定后删除
                .recordStats()
                .build(new CacheLoader<String, String>() {
                    //数据加载，默认返回-1,也可以是查询操作，如从DB查询
                    @Override
                    public String load(String key) throws Exception {
                        return "default";
                    }
                });

        for (int i = 1; i < 100; i++) {
            log.info("=============================");
            messageCache.put("a" + i, "a" + i);
            log.info("cache size:" + messageCache.size());
            log.info(JSON.toJSONString(messageCache.stats().averageLoadPenalty()));
            messageCache.put("b" + i, "b" + i);
            log.info(JSON.toJSONString(messageCache.stats().averageLoadPenalty()));

            log.info(messageCache.getIfPresent("a" + i));
            log.info(JSON.toJSONString(messageCache.stats().averageLoadPenalty()));

            log.info("############################");
            log.info(messageCache.getIfPresent("c" + i));
            log.info(JSON.toJSONString(messageCache.stats().averageLoadPenalty()));
            log.info(JSON.toJSONString(messageCache.stats().totalLoadTime()));
            log.info(JSON.toJSONString(messageCache.stats().loadCount()));
            log.info("messageCache status, hitCount:" + cache.stats().hitCount()
                    + ", missCount:" + cache.stats().missCount());
            log.info("messageCache size:" + messageCache.size());

            //--------------
            dynamicCache.put("a" + i, "a" + i);
            log.info(dynamicCache.getIfPresent("a" + i));
            log.info("dynamicCache stats:" + JSON.toJSONString(dynamicCache.stats()));
            log.info("dynamicCache status, hitCount:" + cache.stats().hitCount()
                    + ", missCount:" + cache.stats().missCount());

            //--------------
            cache.put("a" + i, i);
            log.info("" + cache.getIfPresent("a" + i));
            log.info("cache stats:" + JSON.toJSONString(cache.stats()));
            log.info("cache status, hitCount:" + cache.stats().hitCount()
                    + ", missCount:" + cache.stats().missCount());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        CacheBuilderTest cacheBuilderTest = new CacheBuilderTest();
        cacheBuilderTest.testStats();
    }
}
