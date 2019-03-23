package com.hq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hq.dao.ContentMapper;
import com.hq.dto.ContentQuery;
import com.hq.model.Content;
import com.hq.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author: Mr.Huang
 * @create: 2019-03-20 22:59
 **/
@Slf4j
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public PageInfo<Content> getArticlesByQuery(ContentQuery query, int p, int limit) {

        return null;
    }
}
