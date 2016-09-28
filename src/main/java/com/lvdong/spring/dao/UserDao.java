package com.lvdong.spring.dao;

import com.lvdong.spring.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据
 * Created by lvdong on 2016/9/28.
 */
@Repository
public interface UserDao {
    User selectUserById(@Param("userId") Long userId);

    User selectUserByPhoneOrEmail(@Param("emailOrPhone") String emailOrPhone, @Param("state") Short state);

    List<User> selectAllUser();
}
