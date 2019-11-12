package cn.chay.user.bean;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Created by xiaoni on 2019/11/12.
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String username;
    @Column
    private String name;
    @Column
    private Integer age;
    @Column
    private BigDecimal balance;
}
