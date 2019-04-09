package com.hq.controller.admin;

import com.github.pagehelper.PageInfo;
import com.hq.common.annotion.BussinessLog;
import com.hq.common.constant.Types;
import com.hq.common.exception.BlogException;
import com.hq.common.rest.Result;
import com.hq.controller.BaseController;
import com.hq.dto.ContentQuery;
import com.hq.dto.MetaQuery;
import com.hq.model.Contents;
import com.hq.model.Meta;
import com.hq.service.ContentService;
import com.hq.service.MetaService;
import com.hq.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 文章管理
 * @author: Mr.Huang
 * @create: 2019-04-08 15:42
 **/
@Api("文章管理")
@Controller
@RequestMapping("/admin/article")
@Transactional(rollbackFor = BlogException.class)
@Slf4j
public class ArticleController extends BaseController {

    @Autowired
    private MetaService metaService;

    @Autowired
    private ContentService contentService;

//    @Autowired
//    private CacheManager cacheManager;

    @ApiOperation("文章页")
    public String index(HttpServletRequest request,
                        @ApiParam(name = "page",value = "页数" ,required = false)@RequestParam(name = "page",required = false,defaultValue = "1") int page,
                        @ApiParam(name = "limit",value = "每页数量" ,required = false)@RequestParam(name = "limit",required = false,defaultValue = "10") int limit){

        PageInfo<Contents> articles = contentService.getArticlesByQuery(new ContentQuery(),page,limit);
        request.setAttribute("articles", articles);
        return "admin/articles_list";
    }
    @ApiOperation("发布文章页")
    public String newArticle(HttpServletRequest request){
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setType(Types.CATEGORY.getType());
        List<Meta> metas = metaService.getMetas(metaQuery);
        request.setAttribute("categories", metas);
        return "admin/articles_edit";
    }

    @ApiOperation("发布文章页")
    @RequestMapping(value = "/publish",method = RequestMethod.GET)
    public String publish(HttpServletRequest request){
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setType(Types.CATEGORY.getType());
        List<Meta> metas = metaService.getMetas(metaQuery);
        request.setAttribute("categories", metas);
        return "admin/article_edit";
    }

    @ApiOperation("发布新文章")
    @BussinessLog("发布新文章")
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public Result publishArticle(@ApiParam(name = "title",value = "标题",required = true)@RequestParam(name = "title",required = true)String title,
                                 @ApiParam(name = "titlePic",value = "标题图片",required = false)@RequestParam(name = "titlePic",required = false)String titlePic,
                                 @ApiParam(name = "slug",value = "内容缩略名",required = false)@RequestParam(name = "slug",required = false)String slug,
                                 @ApiParam(name = "content",value = "内容",required = true)@RequestParam(name = "content",required = true)String content,
                                 @ApiParam(name = "type",value = "文章类型",required = true)@RequestParam(name = "type",required = true)String type,
                                 @ApiParam(name = "status",value = "文章状态",required = true)@RequestParam(name = "status",required = true)String status,
                                 @ApiParam(name = "tags",value = "标签",required = false)@RequestParam(name = "tags",required = false)String tags,
                                 @ApiParam(name = "categories",value = "分类",required = false)@RequestParam(name = "categories",required = false,defaultValue = "默认分类")String categories,
                                 @ApiParam(name = "allowComment",value = "是否允许评论",required = true)@RequestParam(name = "allowComment",required = true)Boolean allowComment,
                                 @ApiParam(name = "allowPing",value = "是否允许ping",required = true)@RequestParam(name = "allowPing",required = true)Boolean allowPing,
                                 @ApiParam(name = "allowFeed",value = "是否出行在聚合中",required = true)@RequestParam(name = "allowFeed",required = true)Boolean allowFeed,
                                 @ApiParam(name = "allowShow",value = "是否公开可见",required = true)@RequestParam(name = "allowShow",required = true)Boolean allowShow){
        Contents contents = new Contents();
        contents.setTitle(title);
        contents.setTitlePic(titlePic);
        contents.setSlug(slug);
        contents.setContent(content);
        contents.setTags(type);
        contents.setStatus(status);
        contents.setTags(type.equals(Types.ARTICLE.getType()) ? tags : null);
        //只允许博客文章有分类，防止作品被计入分类
        contents.setCategories(type.equals(Types.ARTICLE.getType())? categories : null);
        contents.setAllowComment(allowComment ? 1 : 0);
        contents.setAllowPing(allowPing ? 1 : 0);
        contents.setAllowFeed(allowFeed ? 1 : 0);
        contents.setAllowShow(allowShow ? 1 : 0);
        contentService.save(contents);
        return ResultUtil.success();
    }

    @ApiOperation("文章编辑页")
    @RequestMapping(value = "/{cid}",method = RequestMethod.GET)
    public String editAtricle(HttpServletRequest request,@ApiParam(name = "cid",value = "文章编号",required = true)
                              @PathVariable Integer cid){
        Contents contents = contentService.getById(cid);
        request.setAttribute("contents", contents);
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setType(Types.CATEGORY.getType());
        List<Meta> categories = metaService.getMetas(metaQuery);
        request.setAttribute("categories", categories);
        request.setAttribute("active", "articles");
        return "/admin/article_edit";
    }

    @ApiOperation("编辑保存文章")
    @BussinessLog("编辑保存文章")
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public @ResponseBody
    Result modifyArticle(@ApiParam(name = "cid",value = "文章主键",required = true)@RequestParam(name = "cid",required = true)Integer cid,
                         @ApiParam(name = "title",value = "标题",required = true)@RequestParam(name = "title",required = true)String title,
                         @ApiParam(name = "titlePic",value = "标题图片",required = false)@RequestParam(name = "titlePic",required = false)String titlePic,
                         @ApiParam(name = "slug",value = "内容缩略名",required = false)@RequestParam(name = "slug",required = false)String slug,
                         @ApiParam(name = "content",value = "内容",required = true)@RequestParam(name = "content",required = true)String content,
                         @ApiParam(name = "type",value = "文章类型",required = true)@RequestParam(name = "type",required = true)String type,
                         @ApiParam(name = "status",value = "文章状态",required = true)@RequestParam(name = "status",required = true)String status,
                         @ApiParam(name = "tags",value = "标签",required = false)@RequestParam(name = "tags",required = false)String tags,
                         @ApiParam(name = "categories",value = "分类",required = false)@RequestParam(name = "categories",required = false,defaultValue = "默认分类")String categories,
                         @ApiParam(name = "allowComment",value = "是否允许评论",required = true)@RequestParam(name = "allowComment",required = true)Boolean allowComment,
                         @ApiParam(name = "allowPing",value = "是否允许ping",required = true)@RequestParam(name = "allowPing",required = true)Boolean allowPing,
                         @ApiParam(name = "allowFeed",value = "是否出行在聚合中",required = true)@RequestParam(name = "allowFeed",required = true)Boolean allowFeed,
                         @ApiParam(name = "allowShow",value = "是否公开可见",required = true)@RequestParam(name = "allowShow",required = true)Boolean allowShow){
        Contents contents = new Contents();
        contents.setCid(cid);
        contents.setTitle(title);
        contents.setTitlePic(titlePic);
        contents.setSlug(slug);
        contents.setContent(content);
        contents.setTags(type);
        contents.setStatus(status);
        contents.setTags(tags);
        contents.setCategories(categories);
        contents.setAllowComment(allowComment ? 1 : 0);
        contents.setAllowPing(allowPing ? 1 : 0);
        contents.setAllowFeed(allowFeed ? 1 : 0);
        contents.setAllowShow(allowShow ? 1 : 0);

        contentService.saveOrUpdate(contents);
        return ResultUtil.success();

    }

    @ApiOperation("删除文章")
    @BussinessLog("删除文章")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@ApiParam(name = "cid", value = "文章主键",required = true)
                         @RequestParam(name = "cid", required = true)Integer cid){
        contentService.removeById(cid);
        return ResultUtil.success();
    }
}
