package com.hq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hq.model.User;

/**
 * Created by huang on 19/3/2019.
 */
public interface UserService extends IService<User>
{

    User getUserInfoById(Integer uid);
}
