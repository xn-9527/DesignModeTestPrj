package cn.chay.movie.vo;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Created by xiaoni on 2019/11/12.
 */
@Data
public class User {
    private Long id;
    private String username;
    private String name;
    private Integer age;
    private BigDecimal balance;
}
