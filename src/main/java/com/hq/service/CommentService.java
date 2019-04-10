package com.hq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hq.dto.CommentQuery;
import com.hq.model.Comment;

/**
 * @author: Mr.Huang
 * @create: 2019-04-02 13:12
 **/
public interface CommentService extends IService<Comment> {
    /**
     * 根据条件查询评论
     * @param commentQuery
     * @param page
     * @param limit
     * @return
     */
    IPage<Comment> getCommentsByQuery(CommentQuery commentQuery, int page, int limit);
}
