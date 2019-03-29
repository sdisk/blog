package com.hq.controller;

import com.hq.common.annotion.BussinessLog;
import com.hq.service.SiteService;
import com.hq.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: blog
 * @description: 网站首页
 * @author: Mr.Huang
 * @create: 2019-03-28 14:20
 **/
@Api("网站首页")
@Controller
@Slf4j
public class HomeController extends BaseController{

    @Autowired
    private SiteService siteService;

    @Autowired
    private UserService userService;

    @ApiOperation("进入首页")
    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    @BussinessLog("进入后台首页")
    public String index(HttpServletRequest request){
        //得到最新的文章
    }
}
