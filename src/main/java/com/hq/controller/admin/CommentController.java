package com.hq.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hq.controller.BaseController;
import com.hq.dto.CommentQuery;
import com.hq.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 评论管理
 * @author: Mr.Huang
 * @create: 2019-04-10 10:04
 **/
@Slf4j
@Api("评论相关接口")
@Controller
@RequestMapping("/admin/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("评论列表")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index(@ApiParam(name = "page",value = "页数",required = false)@RequestParam(name = "page",required = false,defaultValue = "1")int page,
                        @ApiParam(name = "limit",value = "每页条数",required = false)@RequestParam(name = "limit",required = false,defaultValue = "10")int limit, HttpServletRequest request){
        User loginUser = super.getLoginUser(request);
        IPage<Comment> comments = commentService.getCommentsByQuery(new CommentQuery(), page, limit);
        request.setAttribute("comments", comments);
        return "admin/comment_list";
    }
}
