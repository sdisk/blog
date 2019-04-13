package com.hq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.dao.ContentsMapper;
import com.hq.dto.ContentQuery;
import com.hq.model.Contents;
import com.hq.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 * @author: Mr.Huang
 * @create: 2019-03-20 22:59
 **/
@Slf4j
@Service
@CacheConfig(cacheNames={"blogCache"})
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentsMapper contentsMapper;

    @Override
    @Cacheable(value = "atricleCaches",key = "'articlesByQuery_' + #p1 +'type_'+#p0.type ")
    public PageInfo<Contents> getArticlesByQuery(ContentQuery query, int p, int limit) {
        if (null == query){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        PageHelper.startPage(p, limit);
        List<Contents> contents = contentsMapper.getContentsByQuery(query);
        PageInfo<Contents> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }

    @Override
    public int deleteArticlesById(Integer cid)
    {
        //删除文章也要删除相关的评论
        //删除关联的标签和分类
        return 0;
    }

    @Override
    public void save(Contents contents)
    {

    }

    @Override
    public Contents getArticlesById(Integer cid)
    {
        return null;
    }

    @Override
    public void updateArticleById(Contents contents)
    {

    }
}
