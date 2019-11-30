package cn.test.sendHttpMessage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by enva on 2017/5/9.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendHttpMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;//

    private String senderId;//发送者ID

    private String receiverId;//接收者ID或机器序列号I

    private Integer messageKind;//消息种类，默认为0，0：文本消息，1：二进制消息

    private String messageType;//消息类型

    private Integer messageStatusType;//下载状态默认为0，1：未下载，2：下载完成，3：发送ros消息完成

    private String relyMessage;//回执消息

    private String messageText;//文本消息

    private byte[] messageBinary;//二进制消息

    private Integer sendCount;//发送次数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date sendTime;//发送时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updateTime;//接收时间

    private Boolean success;//是否发送成功

    /**
     * 是否需要回执， 1 表示需要 0 表示不需要
     **/
    private String isNeedReturn;

    public SendHttpMessage(String uuid) {
        this.uuid = uuid;
    }
}
