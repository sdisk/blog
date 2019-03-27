package com.hq.controller;

import com.hq.common.constant.Constants;
import com.hq.common.exception.InvalidKaptchaException;
import com.hq.common.rest.Result;
import com.hq.model.User;
import com.hq.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huang on 27/3/2019.
 */
@Controller
@RequestMapping("/admin")
@Api("用户登录相关接口")
@Slf4j
public class AuthController extends BaseController
{
    private static final String LOGIN_ERROR_COUNT = "login_error_count";
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ApiOperation("跳转到登录页")
    public String login(){
        return "/admin/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation("登录")
    public @ResponseBody
    Result login(HttpServletRequest request, HttpServletResponse response,
                 @ApiParam(name = "username",value = "用户名",required = true) @RequestParam(name =
                         "username",required = true) String username,
                 @ApiParam(name = "password",value = "密码",required = true) @RequestParam(name =
                         "password",required = true) String password,
                 @ApiParam(name = "remeberme",value = "记住我",required = false)@RequestParam(name =
                         "remeberme",required = false) String remeberme,
                 @ApiParam(name = "captcha",value = "验证码",required = true) @RequestParam(name =
                         "captcha",required = true) String captcha){
        //判断验证码是否正确
        String code = (String)  super.getSession(request).getAttribute(Constants
                .KAPTCHA_SESSION_KEY);
        if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(captcha)){
            throw new InvalidKaptchaException();
        }

        //对错误次数进行计数
        Integer errorCount = cache.get(LOGIN_ERROR_COUNT);
        User user = userService.login(username, password);
    }
}
