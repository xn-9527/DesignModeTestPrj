package cn.test.sendHttpMessage;

import cn.test.httpProgressBar.AjaxResult;
import cn.test.sendHttpMessage.impl.SendHttpMessageServiceImpl;
import cn.test.sendHttpMessage.impl.UuidCacheServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiaoni on 2019/11/30.
 */
@Slf4j
public class Test {
    SendHttpMessageServiceImpl sendHttpMessageService = new SendHttpMessageServiceImpl();
    UuidCacheServiceImpl uuidCacheService = new UuidCacheServiceImpl();

    private void agentOrderConfirmMessage(String robotSn, String uuid) {
        log.debug(">>>>>>>>>>>>>>agentOrderConfirmMessage try get {} message uuid {}", robotSn, uuid);
        List<SendHttpMessage> messageList = sendHttpMessageService.listByIsSuccessAndAfterSendTime(null, null, robotSn, uuid);
        if (messageList == null || messageList.isEmpty()) {
            log.debug(">>>>>>>>>>>>>>agentOrderConfirmMessage try get {} message uuid {}, result : null", robotSn, uuid);
            return;
        }
        SendHttpMessage message = messageList.get(0);
        log.debug(">>>>>>>>>>>>>>agentOrderConfirmMessage try get {} message uuid {}, result : {}", robotSn, uuid, message);
        //修改状态
        message.setMessageStatusType(-100);
        //更新最近被拉取时间
        message.setUpdateTime(new Date());
        //修改消息状态为成功
        message.setSuccess(true);
        sendHttpMessageService.update(message);

        //需要回执的消息，确认回执(仅仅是agent收到的回执，业务逻辑需要自己的模块取处理)
        if (true) {
            uuidCacheService.setUUIDCache(UUIDUtil.combineUUIDWithRobotCodeAndSuffix(robotSn, uuid, "agent"), new MessageInfo(message));
        }
    }

    public AjaxResult call(String robotSN, MessageInfo messageInfo) {
        log.info("开始写入sendHttpCommandMessage,robotSN=" + robotSN);
        log.debug("robotSN: {},message is : {}", robotSN, messageInfo);
        messageInfo.setSenderId("cloud");
        messageInfo.setMessageStatusType(MessageStatusType.INIT);
        messageInfo.setSuccess(false);
        try {
            saveSendHttpRecord(messageInfo);
            String uuid = messageInfo.getUuId();
            //消息下发是否需要回执
            if (true) {
                log.debug("uuid:{}是需要回执的消息，开始加锁", uuid);
                try {
                    log.info("开始等待消息uuid:{}机器人{}回执:{}", uuid, robotSN, System.currentTimeMillis());
                    String robotUuid = UUIDUtil.combineUUIDWithRobotCodeAndSuffix(robotSN, uuid, "agent");
                    for (int i = 0; i < 50; i++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            log.error(e.getMessage(), e);
                        }
                        //                SendHttpMessage mi = sendHttpMessageService.getByUuid(uuid);
                        MessageInfo mi = uuidCacheService.getUUIDCache(robotUuid);
                        if (mi != null && mi.isSuccess()) {
                            log.info("收到消息{}机器人回执:{}", uuid, System.currentTimeMillis());
                            return AjaxResult.success();
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return AjaxResult.failed(e.getMessage());
                } finally {
                    log.debug(uuid + "尝试释放锁");
                }

                //如果超过时间没有等到回执，清除这条消息
                log.debug("message {} send time out, time {}, clear this unDealMessage", uuid, System.currentTimeMillis());
                SendHttpMessage domain = new SendHttpMessage();
                domain.setUuid(uuid);
                sendHttpMessageService.delete(domain);

                log.info("message {} Time out,robot no response.", uuid);
                return AjaxResult.failed();
            } else {
                log.debug("uuid:{}不需要等待回执的消息，直接返回成功", uuid);
                return AjaxResult.success();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.failed(e.getMessage());
        }
    }

    /**
     * save send http message to db
     *
     * @param messageInfo
     * @throws Exception
     */
    private void saveSendHttpRecord(MessageInfo messageInfo) throws Exception {
        if (messageInfo == null
                || StringUtil.isNullOrEmpty(messageInfo.getUuId())) {
            log.error("消息为空或uuid为空");
            throw new Exception("消息为空或uuid为空");
        }
        //保存发送的消息
        sendHttpMessageService.save(sendHttpMessageService.toSendHttpMessage(messageInfo));
    }

    public static void main(String[] args) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setSuccess(false);
        messageInfo.setUuId(UUIDUtil.get32UUID());
        messageInfo.setMessageText("adsfsfdfs");
        Test test = new Test();
        test.call("test123", messageInfo);

    }
}
