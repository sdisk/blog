package com.hq.dao;

import com.hq.dto.ContentQuery;
import com.hq.model.Comment;
import com.hq.model.Contents;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentsMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Contents record);

    Contents selectByPrimaryKey(Integer cid);

    List<Contents> selectAll();

    int updateByPrimaryKey(Contents record);

    List<Contents> getContentsByQuery(ContentQuery contentQuery);

    Long getArticleCount();

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
}