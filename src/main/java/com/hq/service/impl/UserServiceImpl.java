package com.hq.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hq.common.constant.Constants;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.dao.UserMapper;
import com.hq.model.User;
import com.hq.service.UserService;
import com.hq.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public User login(String username, String password)
    {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            throw new BlogException(BlogExceptionEnum.USERNAME_PASSWORD_ENPTY);
        }
        String pwd = ToolUtil.MD5encode(username+password+ Constants.USER_SALT);
        return null;
    }
}
