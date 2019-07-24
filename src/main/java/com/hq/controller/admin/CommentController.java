package com.hq.controller.admin;

import com.github.pagehelper.PageInfo;
import com.hq.common.annotion.BussinessLog;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.common.rest.Result;
import com.hq.controller.BaseController;
import com.hq.dto.CommentQuery;
import com.hq.model.Comment;
import com.hq.service.CommentService;
import com.hq.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 评论管理
 * @author: Mr.Huang
 * @create: 2019-04-10 10:04
 **/
@Slf4j
@Api("评论相关接口")
@Controller
@RequestMapping("/admin/comments")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("评论列表")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index(@ApiParam(name = "page",value = "页数",required = false)@RequestParam(name = "page",required = false,defaultValue = "1")int page,
                        @ApiParam(name = "limit",value = "每页条数",required = false)@RequestParam(name = "limit",required = false,defaultValue = "10")int limit, HttpServletRequest request){
        PageInfo<Comment> comments = commentService.getCommentsByQuery(new CommentQuery(), page, limit);
        request.setAttribute("comments", comments);
        return "admin/comment_list";
    }

    @ApiOperation("删除评论")
    @BussinessLog("删除评论")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Result deleteComment(@ApiParam(name = "coid", value = "评论编号", required = true)
                         @RequestParam(name = "coid", required = true)Integer coid){
        try{
            Comment comment = commentService.getCommentById(coid);
            if (null == comment){
                throw new BlogException(BlogExceptionEnum.COMMENT_NOT_EXIST);
            }
            commentService.deleteComment(coid);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return ResultUtil.fail(e.getMessage());
        }
        return ResultUtil.success();
    }

    @ApiOperation("更改评论状态")
    @BussinessLog("更改评论状态")
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public @ResponseBody Result changeStatus(@ApiParam(name = "coid", value = "评论主键", required = true)
                                             @RequestParam(name = "coid", required = true)Integer coid,
                                             @ApiParam(name = "status", value = "状态", required = true)
                                             @RequestParam(name = "status", required = true) String status){
        try {
            Comment comment = commentService.getCommentById(coid);
            if (null != comment){
                commentService.updateCommentStatus(coid, status);
            } else {
                return ResultUtil.fail("删除失败");
            }
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return ResultUtil.fail(e.getMessage());
        }
        return ResultUtil.success();
    }
}
