package com.hq.service.impl;

import com.github.pagehelper.PageInfo;
import com.hq.dao.ContentsMapper;
import com.hq.dto.ContentQuery;
import com.hq.model.Contents;
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
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentsMapper contentsMapper;

    @Override
    public PageInfo<Contents> getArticlesByQuery(ContentQuery query, int p, int limit) {

        return null;
    }
}
