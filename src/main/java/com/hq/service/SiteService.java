package com.hq.service;

import com.hq.dto.ArchiveDto;
import com.hq.dto.ContentQuery;
import com.hq.dto.StatisticsDto;
import com.hq.model.Comment;
import com.hq.model.Contents;

import java.util.List;

/**
 * @description: 站点相关
 * @author: Mr.Huang
 * @create: 2019-03-29 11:21
 **/
public interface SiteService {

    List<Comment> getComments(int commentNum);

    List<Contents> getArticles(int articleNum);

    StatisticsDto getStatistics();

    List<ArchiveDto> getArchives(ContentQuery contentQuery);
}
