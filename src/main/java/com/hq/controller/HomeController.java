package com.hq.controller;

import com.hq.common.annotion.BussinessLog;
import com.hq.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

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

    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private MetaService metaService;

    @Autowired
    private OptionService optionService;


    @ApiIgnore
    @RequestMapping(value = {"/about", "/about/index"}, method = RequestMethod.GET)
    public String getAbout(HttpServletRequest request){
        super.blogBaseData(request, null);
        request.setAttribute("active", "about");
        return "site/about";
    }
}
