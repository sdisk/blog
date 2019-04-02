package com.hq.service;

import com.hq.dto.StatisticsDto;
import com.hq.model.Comment;
import com.hq.model.Content;

import java.util.List;

/**
 * @description: 站点相关
 * @author: Mr.Huang
 * @create: 2019-03-29 11:21
 **/
public interface SiteService {
    List<Comment> getComments(int commentNum);

    List<Content> getArticles(int articleNum);

    StatisticsDto getStatistics();
}
