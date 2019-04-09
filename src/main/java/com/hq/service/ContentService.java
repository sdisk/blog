package com.hq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hq.dto.ContentQuery;
import com.hq.model.Contents;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-20 22:54
 **/
public interface ContentService extends IService<Contents> {
    PageInfo<Contents> getArticlesByQuery(ContentQuery query, int p, int limit);
}
