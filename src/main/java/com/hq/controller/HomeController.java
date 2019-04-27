package com.hq.controller;

import com.github.pagehelper.PageInfo;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.common.rest.Result;
import com.hq.dto.ArchiveDto;
import com.hq.dto.ContentQuery;
import com.hq.model.Comment;
import com.hq.model.Contents;
import com.hq.service.*;
import com.hq.utils.*;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

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

    @ApiOperation("文章内容页")
    @RequestMapping(value = "/blog/article/{cid}",method = RequestMethod.GET)
    public String articleDetail(@ApiParam(name = "cid",value = "文章主键",required = true)
                                @PathVariable("cid")Integer cid,HttpServletRequest request){
        Contents article = contentService.getArticlesById(cid);
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
        request.setAttribute("article", article);
        //更新文章的点击量
        this.updateArticleHit(article.getCid(),article.getHits());
        List<Comment> commentList = commentService.getCommentsByCId(cid);
        request.setAttribute("comments",commentList);
        request.setAttribute("active","blog");
        return "site/blog-datails";
    }

    @ApiOperation("归档页")
    @RequestMapping(value = "/blog/archives/{date}", method = RequestMethod.GET)
    public String archives(@ApiParam(name = "date", value = "归档日期", required = true)
                           @PathVariable("date")String date, HttpServletRequest request){
        ContentQuery contentQuery = new ContentQuery();
        Date dateFormat = DateUtil.dateFormat(date, "yyyy年MM月");
        int start = DateUtil.getUnixTimeByDate(dateFormat);
        int end = DateUtil.getUnixTimeByDate(DateUtil.dateAdd(DateUtil.INTERVAL_MONTH,dateFormat,
                1)) - 1;
        contentQuery.setStartTime(start);
        contentQuery.setEndTime(end);
        contentQuery.setType(Types.ARTICLE.getType());
        List<ArchiveDto> archives = siteService.getArchives(contentQuery);
        request.setAttribute("archives_list", archives);
        return "blog/archives";
    }

    @ApiOperation("归档页-按年份")
    @RequestMapping(value = "/blog/archives/year/{year}", method = RequestMethod.GET)
    public String archivesAtYear(@ApiParam(name = "year", value = "归档日期", required = true)
                                 @PathVariable("year")String year,HttpServletRequest request){
        ContentQuery contentQuery = new ContentQuery();
        int start = DateUtil.getUnixTimeByDate(DateUtil.getYearStartDay(year, "yyyy"));
        int end = DateUtil.getUnixTimeByDate(DateUtil.getYearEndDay(year, "yyyy"));
        contentQuery.setStartTime(start);
        contentQuery.setEndTime(end);
        contentQuery.setType(Types.ARTICLE.getType());
        List<ArchiveDto> archives = siteService.getArchives(contentQuery);
        request.setAttribute("archives_list", archives);
        return "blog/archives";
    }

    @ApiOperation("归档页")
    @RequestMapping(value = {"/blog/archives","/blog/archives/index"}, method = RequestMethod.GET)
    public String archives(HttpServletRequest request){
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
        List<ArchiveDto> archives = siteService.getArchives(contentQuery);
        request.setAttribute("archives_list", archives);
        return "blog/archives";
    }

    @ApiOperation("分类")
    @RequestMapping(value = "/blog/categories/{category}")
    public String categories(@ApiParam(name = "category", value = "分类名", required = true)
                             @PathVariable("category")String category, HttpServletRequest request){
        return this.categories(category, 1, 10, request);
    }

    @ApiOperation("分类-分页")
    @RequestMapping(value = "/blog/categories/{category}/page/{page}", method = RequestMethod.GET)
    public String categories(@ApiParam(name = "category", value = "分类名", required = true)
                                 @PathVariable("category")String category,
                             @ApiParam(name = "page", value = "页数", required = true)
                             @PathVariable("page")int page,
                             @ApiParam(name = "limit", value = "条数", required = true)
                                 @RequestParam(name = "limit", required = true, defaultValue = "10")
                                         int limit,
                             HttpServletRequest request){
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
        contentQuery.setCategory(category);
        PageInfo<Contents> articles = contentService.getArticlesByQuery(contentQuery, page, limit);
        request.setAttribute("articles_list", articles);
        request.setAttribute("type", "categories");
        request.setAttribute("param_name", category);
        return "blog/categories";
    }

    @ApiOperation("标签页")
    @RequestMapping(value = "/blog/tag/{tag}", method = RequestMethod.GET)
    public String tags( @ApiParam(name = "tag", value = "标签名", required = true)
                            @PathVariable("tag")
                                    String tag,
                        HttpServletRequest request){
        return this.tags(tag,1,10,request);
    }

    @ApiOperation("标签页-分页")
    @RequestMapping(value = "/blog/tag/{tag}/page/{page}", method = RequestMethod.GET)
    public String tags(
            @ApiParam(name = "tag", value = "标签名", required = true)
            @PathVariable("tag")
                    String tag,
            @ApiParam(name = "page", value = "页数", required = true)
            @PathVariable("page")
                    int page,
            @ApiParam(name = "limit", value = "条数", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "10")
                    int limit,
            HttpServletRequest request
    ){
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setTag(tag);
        contentQuery.setType(Types.ARTICLE.getType());
        PageInfo<Contents> articles = contentService.getArticlesByQuery(contentQuery, page, limit);
//        this.blogBaseData(request,contentCond);//获取公共分类标签等数据
        request.setAttribute("articles_list", articles);
        request.setAttribute("type", "tag");
        request.setAttribute("param_name", tag);
        return "blog/categories";
    }

    @ApiOperation("搜索文章")
    @RequestMapping(value = "/blog/search", method = RequestMethod.GET)
    public String search(@ApiParam(name = "param", value = "搜索的文字", required = true)
                         @RequestParam(name = "param", required = true)String param,
                         HttpServletRequest request){
        return this.search(param, 1, 10, request);
    }

    @ApiOperation("搜索文章-分页")
    @RequestMapping(value = "/blog/search/{param}/page/{page}", method = RequestMethod.GET)
    public String search(
            @ApiParam(name = "param", value = "搜索的文字", required = true)
            @PathVariable("param")
                    String param,
            @ApiParam(name = "page", value = "页数", required = true)
            @PathVariable("page")
                    int page,
            @ApiParam(name = "limit", value = "条数", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "10")
                    int limit,
            HttpServletRequest request
    ){
        PageInfo<Contents> pageInfo = contentService.searchArticle(param, page, limit);
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setType(Types.ARTICLE.getType());
//        this.blogBaseData(request,contentCond);//获取公共分类标签等数据
        request.setAttribute("articles", pageInfo);
        request.setAttribute("type", "search");
        request.setAttribute("param_name", param);
        return "blog/index";
    }

    /**
     * 评论文章
     * @param request
     * @param response
     * @param cid
     * @param coid
     * @param author
     * @param mail
     * @param url
     * @param text
     * @param _csrf_token
     * @return
     */
    @RequestMapping(value = "/blog/comment", method = RequestMethod.POST)
    public @ResponseBody Result comment(HttpServletRequest request,HttpServletResponse response,
                                        @RequestParam(name = "cid", required = true) Integer cid,
                                        @RequestParam(name = "coid", required = false) Integer coid,
                                        @RequestParam(name = "author", required = false) String author,
                                        @RequestParam(name = "mail", required = false) String mail,
                                        @RequestParam(name = "url", required = false) String url,
                                        @RequestParam(name = "text", required = true) String text,
                                        @RequestParam(name = "_csrf_token", required = true) String _csrf_token){
        String ref = request.getHeader("Referer");
        if (StringUtils.isBlank(ref) || StringUtils.isBlank(_csrf_token)){
            return ResultUtil.fail("访问失败");
        }
        String token = cache.hget(Types.CSRF_TOKEN.getType(), _csrf_token);
        if (StringUtils.isBlank(token)){
            return ResultUtil.fail("访问失败");
        }
        if (null == cid || StringUtils.isBlank(text)){
            return ResultUtil.fail("请输入完整后评论");
        }
        if (StringUtils.isNotBlank(author) && author.length() > 50) {
            return ResultUtil.fail("姓名过长");
        }

        if (StringUtils.isNotBlank(mail) && !ToolUtil.isEmail(mail)) {
            return ResultUtil.fail("请输入正确的邮箱格式");
        }

        if (StringUtils.isNotBlank(url) && !PatternUtil.isURL(url)) {
            return ResultUtil.fail("请输入正确的URL格式");
        }

        if (text.length() > 200) {
            return ResultUtil.fail("请输入200个字符以内的评论");
        }

        String val = IPUtil.getIpAddrByRequest(request) + ":" + cid;
        Integer count = cache.hget(Types.COMMENTS_FREQUENCY.getType(), val);
        if (null != count && count > 0) {
            return ResultUtil.fail("您发表评论太快了，请过会再试");
        }

        author = ToolUtil.cleanXSS(author);
        text = ToolUtil.cleanXSS(text);

        author = EmojiParser.parseToAliases(author);
        text = EmojiParser.parseToAliases(text);

        Comment comments = new Comment();
        comments.setAuthor(author);
        comments.setCid(cid);
        comments.setIp(request.getRemoteAddr());
        comments.setUrl(url);
        comments.setContent(text);
        comments.setMail(mail);
        comments.setParentId(coid);
        try {
            commentService.addComment(comments);
            cookie("tale_remember_author", URLEncoder.encode(author, "UTF-8"), 7 * 24 * 60 * 60, response);
            cookie("tale_remember_mail", URLEncoder.encode(mail, "UTF-8"), 7 * 24 * 60 * 60, response);
            if (StringUtils.isNotBlank(url)) {
                cookie("tale_remember_url", URLEncoder.encode(url, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            // 设置对每个文章1分钟可以评论一次
            cache.hset(Types.COMMENTS_FREQUENCY.getType(), val, 1, 60);

            return ResultUtil.success();
        } catch (Exception e) {
            throw new BlogException(BlogExceptionEnum.ADD_NEW_COMMENT_FAIL);
        }
    }

    private void updateArticleHit(Integer cid, Integer chits)
    {
        Integer hits = cache.hget("article","hits");
        if (null == chits){
            chits = 0;
        }
        hits = null == hits ? 1 : hits + 1;
        if (hits >= Constants.HIT_EXCEED){
            Contents temp = new Contents();
            temp.setCid(cid);
            temp.setHits(chits + hits);
            contentService.updateArticleById(temp);
            cache.hset("article", "hits", 1);
        } else {
            cache.hset("article", "hits", hits);
        }
    }

    /**
     * 设置cookie
     *
     * @param name
     * @param value
     * @param maxAge
     * @param response
     */
    private void cookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }
}
