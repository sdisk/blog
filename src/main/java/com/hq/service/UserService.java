package com.hq.service;


import com.hq.model.User;

/**
 * Created by huang on 19/3/2019.
 */
public interface UserService
{

    User getUserInfoById(Integer uid);

    User login(String username, String password);

    int updateUser(User temp);
}
