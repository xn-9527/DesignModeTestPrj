package cn.test.sendHttpMessage;

import java.util.List;

/**
 * @author Created by enva on 2017/5/11.
 */
public interface SendHttpMessageService {

    /**
     * 获取未确认消息列表
     *
     * @param robotCode
     * @return
     */
    List<SendHttpMessage> getUnDealMessages(String robotCode);

    /**
     * save
     *
     * @param message
     * @return
     */
    long save(SendHttpMessage message);

    /**
     * 根据是否成功，发送时间有效范围查询消息
     *
     * @param isSuccess
     * @param sendTime  数据库时间，到秒数
     * @param robotCode
     * @return
     */
    List<SendHttpMessage> listByIsSuccessAndAfterSendTime(Boolean isSuccess, Long sendTime, String robotCode, String uuid);

    /**
     * 根据是否成功，发送时间有效范围查询非任务消息
     *
     * @param isSuccess
     * @param sendTime  数据库时间，到秒数
     * @param robotCode
     * @return
     */
    List<SendHttpMessage> listByIsSuccessAndAfterSendTimeNoMission(Boolean isSuccess, Long sendTime, String robotCode, String uuid);

    /**
     * 根据是否成功，发送时间有效范围,消息类型查询特定类型消息
     *
     * @param isSuccess
     * @param sendTime    数据库时间，到秒数
     * @param robotSn
     * @param messageType
     * @return
     */
    List<SendHttpMessage> listByIsSuccessAndAfterSendTimeAndType(Boolean isSuccess, Long sendTime, String robotSn, String messageType, String uuid);

    /**
     * todo for debug
     */
    void printMessageCacheDebugStats();

    void update(SendHttpMessage message);

    void delete(SendHttpMessage sendHttpMessage);

    /**
     * 转化成messageInfo
     *
     * @param messageInfo
     * @return
     */
    SendHttpMessage toSendHttpMessage(MessageInfo messageInfo);
}
