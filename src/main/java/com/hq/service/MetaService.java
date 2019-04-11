package com.hq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hq.dto.MetaDto;
import com.hq.dto.MetaQuery;
import com.hq.model.Meta;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-20 22:54
 **/
public interface MetaService{

    /**
     * 根据类型查询项目列表，带项目下面的文章数
     * @param type
     * @param orderby
     * @param limit
     * @return
     */
    List<MetaDto> getMetaList(String type, String orderby, int limit);

    /**
     * 获取所有的项目
     * @param metaQuery 查询条件
     * @return
     */
    List<Meta> getMetas(MetaQuery metaQuery);
}
