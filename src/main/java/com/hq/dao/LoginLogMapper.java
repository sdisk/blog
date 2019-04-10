package com.hq.dao;

import com.hq.entity.LoginLog;
import java.util.List;

public interface LoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginLog record);

    LoginLog selectByPrimaryKey(Integer id);

    List<LoginLog> selectAll();

    int updateByPrimaryKey(LoginLog record);
}