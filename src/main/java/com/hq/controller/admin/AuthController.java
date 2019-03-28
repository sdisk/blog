package com.hq.controller.admin;

import com.hq.common.annotion.BussinessLog;
import com.hq.common.constant.Constants;
import com.hq.common.constant.state.LoginLogType;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.common.exception.InvalidKaptchaException;
import com.hq.common.log.LogManager;
import com.hq.common.log.LogTaskFactory;
import com.hq.common.rest.Result;
import com.hq.controller.BaseController;
import com.hq.model.User;
import com.hq.service.UserService;
import com.hq.utils.ResultUtil;
import com.hq.utils.ToolUtil;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    /**
     * 缓存10分钟
     */
    private static final int LOGIN_ERROR_EXPIRES = 600;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ApiOperation("跳转到登录页")
    public String login(){
        return "/admin/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation("登录")
    @BussinessLog("用户登录")
    public @ResponseBody
    Result login(HttpServletRequest request, HttpServletResponse response,
                 @ApiParam(name = "username", value = "用户名", required = true) @RequestParam(name =
                         "username", required = true) String username,
                 @ApiParam(name = "password", value = "密码", required = true) @RequestParam(name =
                         "password", required = true) String password,
                 @ApiParam(name = "remeberme", value = "记住我", required = false) @RequestParam(name =
                         "remeberme", required = false) String remeberme,
                 @ApiParam(name = "captcha", value = "验证码", required = true) @RequestParam(name =
                         "captcha", required = true) String captcha){
        //判断验证码是否正确
        HttpSession httpSession = super.getSession(request);
        String code = (String) httpSession.getAttribute(Constants
                .KAPTCHA_SESSION_KEY);
        if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(captcha)){
            throw new InvalidKaptchaException();
        }
        //对错误次数进行计数
        Integer errorCount = cache.get(LOGIN_ERROR_COUNT);
        try{
            User user = userService.login(username, password);
            //登录成功,清除用户密码信息存入session
            httpSession.setAttribute(Constants.LOGIN_SESSION_KEY, ToolUtil.cleanPwd(user));
            if (StringUtils.isNotBlank(remeberme)){
                ToolUtil.setCookie(response, user.getUid());
            }
            LogManager.getLogManager().executeLog(LogTaskFactory.loginLog(user.getUid(), request.getRemoteHost()));
        } catch (Exception e){
            log.error(e.getMessage());
            errorCount = null == errorCount ? 1 : errorCount + 1;
            if (errorCount > Constants.RETRY_TIME){
                return ResultUtil.fail("您输入密码错误次数超过三次，请10分钟后再登录！");
            }
            cache.set(LOGIN_ERROR_COUNT, errorCount, LOGIN_ERROR_EXPIRES);
            String msg = "登录失败";
            if (e instanceof BlogException){
                msg = e.getMessage();
            } else {
                log.error(msg, e);
            }
            LogManager.getLogManager().executeLog(LogTaskFactory.loginLog(username, msg, request.getRemoteHost()));
            return ResultUtil.fail(msg);
        }

        return ResultUtil.success();
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ApiOperation("注销")
    @BussinessLog("用户注销")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        User user = super.getLoginUser(request);
        if (null == user){
            log.info("用户未登录");
            throw new BlogException(BlogExceptionEnum.USER_NO_LOGIN);
        }
        HttpSession httpSession = super.getSession(request);
        httpSession.removeAttribute(Constants.LOGIN_SESSION_KEY);
        Cookie cookie = new Cookie(Constants.USER_IN_COOKIE, "");
        cookie.setValue("");
        //清除立即生效
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            response.sendRedirect("/admin/login");
            LogManager.getLogManager().executeLog(LogTaskFactory.exitLog(user.getUid(), request.getRemoteHost()));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("用户注销失败", e);
            LogManager.getLogManager().executeLog(LogTaskFactory.loginLog(LoginLogType.EXIT, user.getUid(), e.getMessage(), request.getRemoteHost()));
        }
    }

    @RequestMapping(value = "upPwd", method = RequestMethod.POST)
    @ApiOperation("修改密码")
    @BussinessLog("用户修改密码")
    public @ResponseBody Result upPwd(HttpServletRequest request, HttpServletResponse response,
                                      @ApiParam(name = "oldPassword" ,required = true)
                                      @RequestParam(name = "oldPassword", required = true) String oldPassword,
                                      @ApiParam(name = "newPassword" ,required = true)
                                      @RequestParam(name = "newPassword", required = true) String newPassword){
        User user = super.getLoginUser(request);
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)){
            return ResultUtil.fail("请确认信息是否输入完整");
        }
        if (!user.getPassword().equals(ToolUtil.MD5encode(user.getUserName() + user.getPassword() + Constants.USER_SALT))){
            return ResultUtil.fail("旧密码错误");
        }
        //用户密码在6-14位之间
        if (newPassword.length() < 6 || newPassword.length() > 14){
            return ResultUtil.fail("请输入6-14位密码");
        }
        User temp = new User();
        try{
            temp.setUid(user.getUid());
            temp.setPassword(ToolUtil.MD5encode(user.getUserName() + user.getPassword() + Constants.USER_SALT));
            userService.updateById(temp);
            LogManager.getLogManager().executeLog(LogTaskFactory.loginLog(LoginLogType.UP_PASSWORD, user.getUid(),"",  request.getRemoteHost()));
            return ResultUtil.success();
        } catch (Exception e){
            String msg = "密码修改失败";
            if (e instanceof BlogException) {
                msg = e.getMessage();
            } else {
                log.error(msg, e);
            }
            LogManager.getLogManager().executeLog(LogTaskFactory.loginLog(LoginLogType.UP_PASSWORD_EXCEPTION, user.getUid(),"",  request.getRemoteHost()));
            return ResultUtil.fail(msg);
        }

    }

}
