package com.hq.service;

import com.github.pagehelper.PageInfo;
import com.hq.dto.CommentQuery;
import com.hq.model.Comment;

import java.util.List;

/**
 * @author: Mr.Huang
 * @create: 2019-04-02 13:12
 **/
public interface CommentService{
    /**
     * 根据条件查询评论
     * @param commentQuery
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Comment> getCommentsByQuery(CommentQuery commentQuery, int page, int limit);

    List<Comment> getCommentsByCId(Integer cid);

    void addComment(Comment comments);

    Comment getCommentById(Integer coid);

    void deleteComment(Integer coid);

    void updateCommentStatus(Integer coid, String status);

    /**
     * 查询文章的树形评论
     * @param cid
     * @return
     */
    List<Comment> getAllCommentsByCId(Integer cid);
}
