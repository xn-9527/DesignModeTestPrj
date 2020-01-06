package cn.chay.user.dao;

import cn.chay.user.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by xiaoni on 2019/11/12.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
