package com.hq.dao;

import com.hq.dto.MetaDto;
import com.hq.dto.MetaQuery;
import com.hq.model.Meta;

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
}