package com.hq.controller.admin;

import com.github.pagehelper.PageInfo;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.controller.BaseController;
import com.hq.dto.ContentQuery;
import com.hq.model.Content;
import com.hq.service.ContentService;
import com.hq.service.OptionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 首页
 * @author: Mr.Huang
 * @create: 2019-03-23 18:21
 **/
@Controller
@Api("网站后台首页")
@RequestMapping("/admin")
public class IndexController extends BaseController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private OptionsService optionsService;

    @ApiIgnore
    @GetMapping(value = {"/about", "/about/index"})
    public String getAbout(HttpServletRequest request){
        this.blogBaseData(request, null);
        request.setAttribute("active", "about");
        return "site/about";
    }

    @ApiOperation("blog首页")
    @GetMapping(value = {"/blog/", "/blog/index"})
    public String blogIndex(HttpServletRequest request,
                            @ApiParam(name = "limit", value = "页数",  required = false)
                            @RequestParam(name = "limit", required = false, defaultValue = "11") int limit){
        return this.blogIndex(request, 1, limit);
    }

    @ApiOperation("blog首页-分页")
    @GetMapping(value = "/blog/page/{b}")
    public String blogIndex(HttpServletRequest request, @PathVariable("p") int p ,
                            @RequestParam(value = "limit", required = false, defaultValue = "11") int limit){
        p = p < 0 || p > Constants.MAX_PAGE ? 1 : p;
        ContentQuery query = new ContentQuery();
        query.setType(Types.ARTICLE.getType());
        PageInfo<Content> articles = contentService.getArticlesByQuery(query, p, limit);
    }
}
