package com.hq.dao;

import com.hq.dto.ArchiveDto;
import com.hq.dto.ContentQuery;
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
     * 更新文章
     * @param contents
     */
    void updateArticleById(Contents contents);

    List<ArchiveDto> getArchive(ContentQuery contentQuery);

    List<Contents> searchArticle(String param);

    /**
     * 更新文章的评论数
     * @param cid
     * @param commentsNum
     * @return
     */
    int updateArticleCommentCountById(@Param("cid") Integer cid, @Param("commentsNum") Integer commentsNum);

}