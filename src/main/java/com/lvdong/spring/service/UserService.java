package com.lvdong.spring.service;

import com.lvdong.spring.model.User;

import java.util.List;

/**
 * Created by lvdong on 2016/9/28.
 */
public interface UserService {
    List<User> getAllUser();

    User getUserByPhoneOrEmail(String emailOrPhone, Short state);

    User getUserById(Long userId);
}
