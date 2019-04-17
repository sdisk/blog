package com.hq.service;

import com.github.pagehelper.PageInfo;
import com.hq.dto.ContentQuery;
import com.hq.model.Contents;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-20 22:54
 **/
public interface ContentService {

    PageInfo<Contents> getArticlesByQuery(ContentQuery query, int page, int limit);

    void deleteArticlesById(Integer cid);

    void save(Contents contents);

    Contents getArticlesById(Integer cid);

    void updateArticleById(Contents contents);

    void updateCategory(String name, String name1);
}
