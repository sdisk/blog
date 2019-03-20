package com.hq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hq.dao.UserMapper;
import com.hq.model.User;
import com.hq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Mr.Huang
 * @create: 2019-03-20 11:04
 **/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserInfoById(Integer uid) {
        return userMapper.selectById(uid);
    }
}
