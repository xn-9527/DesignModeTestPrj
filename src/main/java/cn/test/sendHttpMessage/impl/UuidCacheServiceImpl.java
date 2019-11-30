package cn.test.sendHttpMessage.impl;

import cn.test.sendHttpMessage.MessageInfo;
import cn.test.sendHttpMessage.UuidCacheService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Created by xiaoni on 2019/11/26.
 */
@Service
public class UuidCacheServiceImpl implements UuidCacheService {

    private final Cache<String, MessageInfo> uuidCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build();

    @Override
    public synchronized void setUUIDCache(String uuid, MessageInfo messageInfo) {
        uuidCache.put(uuid, messageInfo);
    }

    @Override
    public synchronized MessageInfo getUUIDCache(String uuid) {
        return uuidCache.getIfPresent(uuid);
    }

}
