package com.hq.controller;

import com.hq.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by huang on 27/3/2019.
 */
@Controller
@RequestMapping("/user")
@Api("用户管理类")
@Slf4j
public class UserController extends BaseController
{
    @Autowired
    private UserService userService;

}
