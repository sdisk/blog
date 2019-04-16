package com.hq.dao;

import com.hq.dto.MetaDto;
import com.hq.dto.MetaQuery;
import com.hq.model.Meta;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MetaMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Meta record);

    Meta selectByPrimaryKey(Integer mid);

    List<Meta> selectAll();

    int updateByPrimaryKey(Meta record);

    List<MetaDto> selectMetaDtoByMap(Map<String, Object> paraMap);

    List<Meta> getMetasByMetaQuery(MetaQuery metaQuery);

    Long getMetasCountByType(String type);

    /**
     * 根据编号获取项目
     * @param mid
     * @return
     */
    Meta getMetaById(@Param("mid") Integer mid);

    /**
     * 删除项目
     * @param mid
     */
    void deleteById(@Param("mid") Integer mid);

    /**
     * 更新项目
     * @param meta
     */
    void updateMeta(Meta meta);

    /**
     * 添加项目
     * @param domain
     */
    void addMeta(Meta domain);
}