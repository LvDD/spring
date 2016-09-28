package com.lvdong.spring.service.impl;

import com.lvdong.spring.dao.UserDao;
import com.lvdong.spring.model.User;
import com.lvdong.spring.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户服务
 * Created by lvdong on 2016/9/28.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public List<User> getAllUser() {
        return userDao.selectAllUser();
    }

    public User getUserByPhoneOrEmail(String emailOrPhone, Short state) {
        return userDao.selectUserByPhoneOrEmail(emailOrPhone,state);
    }

    public User getUserById(Long userId) {
        return userDao.selectUserById(userId);
    }
}
