package com.hq.service.impl;

import com.github.pagehelper.PageHelper;
import com.hq.common.constant.Types;
import com.hq.dao.AttachMapper;
import com.hq.dao.CommentMapper;
import com.hq.dao.ContentsMapper;
import com.hq.dao.MetaMapper;
import com.hq.dto.ArchiveDto;
import com.hq.dto.CommentQuery;
import com.hq.dto.ContentQuery;
import com.hq.dto.StatisticsDto;
import com.hq.model.Comment;
import com.hq.model.Contents;
import com.hq.service.SiteService;
import com.hq.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-29 11:23
 **/
@Service
@Slf4j
@CacheConfig(cacheNames = {"blogCache"})
public class SiteServiceImpl implements SiteService {

    private static final String CACHE_PREFIX = "siteCache";

    private static final int PAGESIZE = 10;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ContentsMapper contentsMapper;

    @Autowired
    private MetaMapper metaMapper;

    @Autowired
    private AttachMapper attachMapper;


    @Override
    @Cacheable(value = CACHE_PREFIX, key = "'comments_' + #p0")
    public List<Comment> getComments(int commentNum) {
        if (commentNum < 0 || commentNum > PAGESIZE){
            commentNum = PAGESIZE;
        }
        //PageHelper.startPage 物理分页
        PageHelper.startPage(1, commentNum);
        List<Comment> comments = commentMapper.getCommentsByQuery(new CommentQuery());
        return comments;
    }

    @Override
    @Cacheable(value = CACHE_PREFIX, key = "'articles_' + #p0")
    public List<Contents> getArticles(int articleNum) {
        if (articleNum < 0 || articleNum > PAGESIZE){
            articleNum = PAGESIZE;
        }
        PageHelper.startPage(1, articleNum);
        List<Contents> contents = contentsMapper.getContentsByQuery(new ContentQuery());
        return contents;
    }

    @Override
    @Cacheable(value = CACHE_PREFIX, key = "'statistics_'")
    public StatisticsDto getStatistics() {
        //文章总数量
        Long artices = contentsMapper.getArticleCount();
        //评论总数量
        Long comments = commentMapper.getCommentCount();
        //链接总数量
        Long links = metaMapper.getMetasCountByType(Types.LINK.getType());
        //附件总数量
        Long attachs = attachMapper.getAttachCount();
        StatisticsDto dto = new StatisticsDto();
        dto.setArticles(artices);
        dto.setComments(comments);
        dto.setLinks(links);
        dto.setAttaches(attachs);
        return dto;
    }

    @Override
    @Cacheable(value = CACHE_PREFIX, key = "'archives_'+#p0")
    public List<ArchiveDto> getArchives(ContentQuery contentQuery)
    {
        List<ArchiveDto> archives = contentsMapper.getArchive(contentQuery);
        parseArchives(archives, contentQuery);
        return archives;
    }

    private void parseArchives(List<ArchiveDto> archives, ContentQuery contentQuery)
    {
        if (null != archives){
            archives.forEach(archiveDto -> {
                String date = archiveDto.getDate();
                Date sd = DateUtil.dateFormat(date, "yyyy年MM月");
                int start = DateUtil.getUnixTimeByDate(sd);
                int end = DateUtil.getUnixTimeByDate(DateUtil.dateAdd(DateUtil.INTERVAL_MONTH, sd, 1)) - 1;
                ContentQuery cond = new ContentQuery();
                cond.setStartTime(start);
                cond.setEndTime(end);
                cond.setType(contentQuery.getType());
                List<Contents> contentList = contentsMapper.getContentsByQuery(cond);
                archiveDto.setArticles(contentList);
            });
        }
    }
}
