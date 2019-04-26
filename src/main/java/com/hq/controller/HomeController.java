package com.hq.controller;

import com.github.pagehelper.PageInfo;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.dto.ContentQuery;
import com.hq.model.Contents;
import com.hq.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation("blog首页")
    @RequestMapping(value = {"/blog/","/blog/index"},method = RequestMethod.GET)
    public String blogIndex(HttpServletRequest request, @ApiParam(name = "limit", value = "页数", required = false)
                            @RequestParam(name = "limit", required = false, defaultValue = "11")int limit){
        return this.blogIndex(request, 1 ,limit);
    }

    @ApiOperation("blog首页-分页")
    @RequestMapping(value = "/blog/page/{p}", method = RequestMethod.GET)
    public String blogIndex(HttpServletRequest request, @PathVariable("p") int p,
                            @RequestParam(value = "limit",required = false, defaultValue = "11")int limit){
        p = p < 0 || p > Constants.MAX_PAGE ? 1 : p;
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
        PageInfo<Contents> articles = contentService.getArticlesByQuery(contentQuery, p ,limit);
        request.setAttribute("articles", articles);
        request.setAttribute("type", "articles");
        request.setAttribute("active", "blog");
        return "site/blog";
    }

}
