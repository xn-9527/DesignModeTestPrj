package cn.chay.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Chay
 * @date 2020/4/7 10:55
 */
@TableName(value = "user_info")
@Data
@Api("user entity")
public class UserInfo {
    @ApiModelProperty("user id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("user age")
    private Integer age;
    @ApiModelProperty("user name")
    private String name;
}
