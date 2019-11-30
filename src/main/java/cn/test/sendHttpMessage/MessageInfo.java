package cn.test.sendHttpMessage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.*;
import java.util.Date;

/**
 * Created by enva on 2017/5/9.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MessageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuId;//uuid

    private String senderId;//发送ID或机器序列号

    private String receiverId;//接收者ID或机器序列号

    private Integer messageKind;//消息种类，默认为0，0：文本消息，1：二进制消息

    private MessageType messageType;//消息类型

    private MessageStatusType messageStatusType;//消息状态

    private String relyMessage;//回执消息

    private String messageText;//文本消息

    private byte[] messageBinary;//二进制消息

    private Integer sendCount;//发送次数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;//发送时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//接收时间

    private boolean success;//是否发送成功

    // 是否需要回执， 1 表示需要 0 表示不需要
    private String isNeedReturn;

    public MessageInfo(String senderId, String receiverId, String messageText) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.uuId = UUIDUtil.get32UUID();
        this.sendTime = new Date();
    }


    public MessageInfo(SendHttpMessage message) {
        BeanUtils.copyProperties(message, this);
        this.uuId = message.getUuid();
        if (message != null) {
            if (!StringUtil.isEmpty(message.getMessageType())) {
                this.messageType = MessageType.valueOf(message.getMessageType());
            }
            if (message.getMessageStatusType() > 0) {
                this.messageStatusType = MessageStatusType.getType(message.getMessageStatusType());
            }
        }
    }

    /**
     * 序列化的类的深拷贝
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public synchronized MessageInfo deepClone() throws IOException, ClassNotFoundException {
        // 将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);
        // 从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (MessageInfo) (oi.readObject());
    }
}

