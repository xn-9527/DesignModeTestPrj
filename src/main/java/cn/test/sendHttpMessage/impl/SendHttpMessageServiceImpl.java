package cn.test.sendHttpMessage.impl;

import cn.test.clone.deep.serializable.MyCloneUtils;
import cn.test.sendHttpMessage.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author Created by enva on 2017/5/11.
 */

@Service
@Slf4j
public class SendHttpMessageServiceImpl implements SendHttpMessageService {
    private final static String ORDER_ID = "orderId";
    private final static String DATA = "data";
    private final static String PUBLISH_MESSAGE = "publishMessage";
    private final static LinkedHashMap EMPTY_LIKED_HASH_MAP = new LinkedHashMap<>(0);

    /**
     * 消息缓存：
     * Key: robotSn
     * value: 消息LinkedHashMap保证消息有序, key是消息uuid，value是消息体
     */
    private final Cache<String, LinkedHashMap<String, SendHttpMessage>> robotMessageCache = CacheBuilder.newBuilder()
            //过期时间未写后X秒
            /**
             * 注意：value为更新操作会重置计时器。
             * 但是写入后过期的风险就是不管有没有访问，在没有被更新或写入的时候缓存有可能被强制清空，导致丢失数据
             */
            .expireAfterWrite(10, TimeUnit.SECONDS)
            //todo debug用，稳定后删除
            .recordStats()
            .build();

    /**
     * 机器人消息缓存锁：
     * Key: robotSn
     * value: 锁
     */
    private final Cache<String, ReentrantLock> robotMessageLockCache = CacheBuilder.newBuilder()
            //过期时间未访问后1小时
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build();

    @Override
    public List<SendHttpMessage> getUnDealMessages(String robotCode) {
        if (robotCode == null) {
            return null;
        }
        List<SendHttpMessage> sendHttpMessageList = Collections.emptyList();

        //需要校验锁
        ReentrantLock reentrantLock = getRobotMessageLockCache(robotCode);
        //未获取到锁等待
        reentrantLock.lock();
        try {
            if (false) {
                log.info("机器人{}系统状态参数异常：未准备好,不拉取普通订单的任务消息", robotCode);
                sendHttpMessageList = this.listByIsSuccessAndAfterSendTimeNoMission(false, null, robotCode, null);
            } else {
                log.info("机器人{}系统状态参数正常,拉取所有未处理消息，含普通订单的任务消息", robotCode);
                //取发送时间有效范围内（定时任务会清除5分钟以上的消息）、未处理的消息,含任务消息
                sendHttpMessageList = this.listByIsSuccessAndAfterSendTime(false, null, robotCode, null);
            }

            //把消息状态改成：已拉取
            if (sendHttpMessageList != null && !sendHttpMessageList.isEmpty()) {
                sendHttpMessageList.forEach(sendHttpMessage -> {
                    try {
                        //修改状态
                        sendHttpMessage.setMessageStatusType(-100);
                        //被拉取次数加1
                        Integer lastCount = sendHttpMessage.getSendCount();
                        lastCount = lastCount == null ? 0 : lastCount;
                        sendHttpMessage.setSendCount(lastCount + 1);
                        updateRobotMessageCache(robotCode, sendHttpMessage);
                    } catch (Exception e) {
                        log.error("更新状态为已拉取失败：{}", sendHttpMessage);
                        log.error(e.getMessage(), e);
                    }
                });
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
        return sendHttpMessageList;
    }

    @Override
    public void delete(SendHttpMessage sendHttpMessage) {
        log.info("delete sendHttpMessage: {}", sendHttpMessage);
        if (sendHttpMessage == null || StringUtil.isNullOrEmpty(sendHttpMessage.getReceiverId())) {
            return;
        }
        removeRobotMessageCache(sendHttpMessage.getReceiverId(), sendHttpMessage);
    }

    @Override
    public long save(SendHttpMessage message) {
        log.info("save sendHttpMessage: {}", message);
        if (null == message || StringUtil.isNullOrEmpty(message.getReceiverId())) {
            return 0L;
        }
        message.setSendTime(new Date());
        updateRobotMessageCache(message.getReceiverId(), message);
        return 1L;
    }

    @Override
    public void update(SendHttpMessage message) {
        log.info("update sendHttpMessage: {}", message);
        if (null == message || StringUtil.isNullOrEmpty(message.getReceiverId())) {
            return;
        }
        message.setUpdateTime(new Date());
        updateRobotMessageCache(message.getReceiverId(), message);
    }

    /**
     * 根据是否成功，发送时间有效范围查询消息
     *
     * @param isSuccess
     * @param sendTime  数据库时间，到秒数
     * @param robotCode
     */
    @Override
    public List<SendHttpMessage> listByIsSuccessAndAfterSendTime(Boolean isSuccess, Long sendTime, String robotCode, String uuid) {
        return listByCondition(isSuccess, sendTime, robotCode, null, null, uuid);
    }

    /**
     * 根据是否成功，发送时间有效范围查询非任务消息
     *
     * @param isSuccess
     * @param sendTime  数据库时间，到秒数
     * @param robotCode
     */
    @Override
    public List<SendHttpMessage> listByIsSuccessAndAfterSendTimeNoMission(Boolean isSuccess, Long sendTime, String robotCode, String uuid) {
        return listByCondition(isSuccess, sendTime, robotCode, "EXECUTOR_MISSION", true, uuid);
    }

    /**
     * listByCondition
     *
     * @param isSuccess
     * @param sendTime
     * @param robotSn
     * @param messageType
     * @param isExcludeMessageType 是否排除消息类型，生效的前提是messageType不为空，如果是true，表示排除；为null或者false表示根据类型查询
     * @return
     */
    private List<SendHttpMessage> listByCondition(Boolean isSuccess, Long sendTime, String robotSn,
                                                  String messageType, Boolean isExcludeMessageType, String uuid) {
        LinkedHashMap<String, SendHttpMessage> messageLinkedHashMap = getRobotMessageCache(robotSn, true);
        if (!messageLinkedHashMap.isEmpty()) {
            Iterator<Map.Entry<String, SendHttpMessage>> it = messageLinkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, SendHttpMessage> entry = it.next();
                SendHttpMessage sendHttpMessage = entry.getValue();
                if (uuid != null && uuid.equals(entry.getKey())) {
                    List<SendHttpMessage> list = Lists.newArrayList();
                    list.add(sendHttpMessage);
                    return list;
                }
                //判断isSuccess:不需判断的，则保留；需要判断的，消息体success为null和不相等的直接移除
                boolean isRemoveBySuccessCondition = isSuccess != null
                        && (sendHttpMessage.getSuccess() == null
                        || (sendHttpMessage.getSuccess() != null && !isSuccess.equals(sendHttpMessage.getSuccess())));
                if (isRemoveBySuccessCondition) {
                    it.remove();
                }
                //判断sendTime
                boolean isRemoveBySendTimeCondition = sendTime != null
                        && (sendHttpMessage.getSendTime() == null || (sendHttpMessage.getSendTime() != null && sendHttpMessage.getSendTime().getTime() < sendTime));
                if (isRemoveBySendTimeCondition) {
                    it.remove();
                }
                //判断messageType
                isExcludeMessageType = isExcludeMessageType == null ? false : isExcludeMessageType;
                if (messageType != null) {
                    boolean isRemoveByMessageTypeCondition = sendHttpMessage.getMessageType() == null;
                    if (isExcludeMessageType) {
                        //如果排除类型，则要判断该类型的消息都要排除
                        isRemoveByMessageTypeCondition = isRemoveByMessageTypeCondition
                                || (sendHttpMessage.getMessageType() != null && messageType.equals(sendHttpMessage.getMessageType()));
                    } else {
                        //如果不排除类型，则要判断非该类型的消息都要排除
                        isRemoveByMessageTypeCondition = isRemoveByMessageTypeCondition
                                || (sendHttpMessage.getMessageType() != null && !messageType.equals(sendHttpMessage.getMessageType()));
                    }

                    if (isRemoveByMessageTypeCondition) {
                        it.remove();
                    }
                }
            }
        }
        if (messageLinkedHashMap.isEmpty()) {
            return Collections.emptyList();
        } else {
            return messageLinkedHashMap.values().stream().collect(Collectors.toList());
        }
    }

    /**
     * 根据是否成功，发送时间有效范围,消息类型查询特定类型消息
     *
     * @param isSuccess
     * @param sendTime    数据库时间，到秒数
     * @param robotSn
     * @param messageType
     */
    @Override
    public List<SendHttpMessage> listByIsSuccessAndAfterSendTimeAndType(Boolean isSuccess, Long sendTime, String robotSn, String messageType, String uuid) {
        return listByCondition(isSuccess, sendTime, robotSn, messageType, false, uuid);
    }

    /**
     * 获取下发消息缓存
     *
     * @param robotSn
     * @param isClone
     * @return key是消息的uuid，value是消息体
     */
    private LinkedHashMap<String, SendHttpMessage> getRobotMessageCache(String robotSn, boolean isClone) {
        //需要校验锁
        ReentrantLock reentrantLock = getRobotMessageLockCache(robotSn);
        //未获取到锁等待
        reentrantLock.lock();
        LinkedHashMap<String, SendHttpMessage> sendHttpMessages = robotMessageCache.getIfPresent(robotSn);
        try {
            if (sendHttpMessages == null) {
                //init first time
                sendHttpMessages = new LinkedHashMap<>();
                robotMessageCache.put(robotSn, sendHttpMessages);
            }
            if (!sendHttpMessages.isEmpty()) {
                //清除过期消息, “当前时间 > 发送时间 + 等待时间”的即为过期消息，换算为“发送时间 < 当前时间 - 等待时间”
                Long timeExpired = System.currentTimeMillis() - 50 * 1000L;
                Iterator<Map.Entry<String, SendHttpMessage>> it = sendHttpMessages.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, SendHttpMessage> entry = it.next();
                    SendHttpMessage sendHttpMessage = entry.getValue();
                    if (sendHttpMessage.getSendTime().getTime() < timeExpired) {
                        it.remove();
                    }
                }
            }
            //如果需要克隆
            if (isClone) {
                if (sendHttpMessages.isEmpty()) {
                    return EMPTY_LIKED_HASH_MAP;
                }
                sendHttpMessages = MyCloneUtils.deepCloneMap2(sendHttpMessages, SendHttpMessage.class);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
        return sendHttpMessages;
    }

    /**
     * update
     *
     * @param robotSn
     * @param httpMessage
     */
    private void updateRobotMessageCache(String robotSn, SendHttpMessage httpMessage) {
        if (httpMessage == null) {
            return;
        }
        //需要校验锁
        ReentrantLock reentrantLock = getRobotMessageLockCache(robotSn);
        //未获取到锁等待
        reentrantLock.lock();
        try {
            LinkedHashMap<String, SendHttpMessage> sendHttpMessages = getRobotMessageCache(robotSn, false);
            if (StringUtil.isNullOrEmpty(httpMessage.getUuid())) {
                httpMessage.setUuid(UUIDUtil.get32UUID());
            }
            sendHttpMessages.put(httpMessage.getUuid(), httpMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
    }

    /**
     * remove
     *
     * @param robotSn
     * @param httpMessage
     */
    private void removeRobotMessageCache(String robotSn, SendHttpMessage httpMessage) {
        if (null == httpMessage || StringUtil.isNullOrEmpty(httpMessage.getUuid())) {
            return;
        }
        //需要校验锁
        ReentrantLock reentrantLock = getRobotMessageLockCache(robotSn);
        //未获取到锁等待
        reentrantLock.lock();
        try {
            LinkedHashMap<String, SendHttpMessage> sendHttpMessages = getRobotMessageCache(robotSn, false);
            sendHttpMessages.remove(httpMessage.getUuid());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
    }

    /**
     * 获取路径生成锁缓存
     *
     * @param robotSn
     * @return
     */
    private synchronized ReentrantLock getRobotMessageLockCache(String robotSn) {
        ReentrantLock reentrantLock = robotMessageLockCache.getIfPresent(robotSn);
        if (reentrantLock == null) {
            //第一次初始化
            reentrantLock = new ReentrantLock();
            robotMessageLockCache.put(robotSn, reentrantLock);
        }
        return reentrantLock;
    }

    @Override
    public synchronized void printMessageCacheDebugStats() {
        log.info("robotMessageCache status, hitCount1: {}, missCount: {}, hitRate: {}",
                robotMessageCache.stats().hitCount(),
                robotMessageCache.stats().missCount(),
                robotMessageCache.stats().hitRate());
    }

    @Override
    public SendHttpMessage toSendHttpMessage(MessageInfo messageInfo) {
        SendHttpMessage sendHttpMessage = new SendHttpMessage();
        if (messageInfo != null) {
            BeanUtils.copyProperties(messageInfo, sendHttpMessage);
            sendHttpMessage.setUuid(messageInfo.getUuId());
            if (messageInfo.getMessageType() != null && messageInfo.getMessageType().name() != null) {
                sendHttpMessage.setMessageType(messageInfo.getMessageType().name());
            }
            if (messageInfo.getMessageStatusType() != null && messageInfo.getMessageStatusType().getIndex() > 0) {
                sendHttpMessage.setMessageStatusType(messageInfo.getMessageStatusType().getIndex());
            }
        }
        return sendHttpMessage;
    }
}
