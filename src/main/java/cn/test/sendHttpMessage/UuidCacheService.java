package cn.test.sendHttpMessage;

/**
 * @author Created by chay on 2019/11/26.
 */
public interface UuidCacheService {

    /**
     * setUUIDCache
     *
     * @param uuId
     * @param messageInfo
     */
    void setUUIDCache(String uuId, MessageInfo messageInfo);

    /**
     * getUUIDCache
     *
     * @param uuid
     * @return
     */
    MessageInfo getUUIDCache(String uuid);
}
