package com.hq.dao;

import com.hq.model.Comment;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer coid);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer coid);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);
}