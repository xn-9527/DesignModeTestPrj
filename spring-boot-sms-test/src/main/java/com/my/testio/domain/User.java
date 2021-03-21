package com.my.testio.domain;

import lombok.Data;

/**
 * @Author: xiaoni
 * @Date: 2021/3/21 17:56
 * DROP TABLE IF EXISTS `t_user`;
 * CREATE TABLE `t_user` (
 *   `id` bigint(11) NOT NULL AUTO_INCREMENT,
 *   `name` varchar(255) DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 */
@Data
public class User {
    private long id;
    private String name;
}
