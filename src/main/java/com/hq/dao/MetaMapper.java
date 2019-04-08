package com.hq.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hq.dto.MetaDto;
import com.hq.dto.MetaQuery;
import com.hq.model.Meta;

import java.util.List;
import java.util.Map;

/**
 * @author: Mr.Huang
 * @create: 2019-03-20 23:02
 **/
public interface MetaMapper extends BaseMapper<Meta> {

    List<MetaDto> selectMetaDtoByMap(Map<String, Object> paraMap);

    Long getMetasCountByType(String type);

    List<Meta> getMetasByMetaQuery(MetaQuery metaQuery);
}
