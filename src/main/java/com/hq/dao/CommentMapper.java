package com.hq.dao;

import com.hq.dto.CommentQuery;
import com.hq.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {

    int deleteByPrimaryKey(Integer coid);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer coid);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);

    List<Comment> getCommentsByQuery(CommentQuery commentQuery);

    Long getCommentCount();

    /**
     * 获取单条评论
     * @param coid
     * @return
     */
    Comment getCommentById(@Param("coid") Integer coid);

    /**
     * 根据文章编号获取评论列表
     * @param cid
     * @return
     */
    List<Comment> getCommentsByCid(@Param("cid")Integer cid);

    /**
     * 添加评论
     * @param comments
     */
    void addComment(Comment comments);

    /**
     * 更新评论状态
     * @param coid
     * @param status
     */
    void updateCommentStatus(@Param("coid") Integer coid, @Param("status") String status);
}