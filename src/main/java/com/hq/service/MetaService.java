package com.hq.service;

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

    /**
     * 批量添加
     * @param cid
     * @param names
     * @param type
     */
    void addMetas(Integer cid, String names, String type);

    /**
     * 添加项目
     * @param meta
     */
    void addMeta(Meta meta);

    /**
     * 添加
     * @param type
     * @param name
     * @param mid
     */
    void saveMeta(String type, String name, Integer mid);

    /**
     * 添加或者更新
     * @param cid
     * @param name
     * @param type
     */
    void saveOrUpdate(Integer cid, String name, String type);

    /**
     * 删除项目
     * @param mid
     */
    void deleteMetaById(Integer mid);

    /**
     * 更新项目
     * @param meta
     */
    void updateMeta(Meta meta);

    /**
     * 根据编号获取项目
     * @param mid
     * @return
     */
    Meta getMetaById(Integer mid);
}
