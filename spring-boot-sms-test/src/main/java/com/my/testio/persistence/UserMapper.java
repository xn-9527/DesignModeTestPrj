package com.my.testio.persistence;

import com.my.testio.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xiaoni
 * @Date: 2021/3/21 17:56
 */
@Mapper
public interface UserMapper {
    int insert(User user);
}
