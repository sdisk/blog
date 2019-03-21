package com.hq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hq.common.constant.Constants;
import com.hq.dao.MetaMapper;
import com.hq.dto.MetaDto;
import com.hq.model.Meta;
import com.hq.service.MetaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-20 23:01
 **/
@Slf4j
@Service
public class MetaServiceImpl extends ServiceImpl<MetaMapper, Meta> implements MetaService {

    @Autowired
    private MetaMapper metaMapper;

    @Override
    @Cacheable(value = "metaCaches" , key = "'metaList_' + #p0")
    public List<MetaDto> getMetaList(String type, String orderby, int limit) {

        if (StringUtils.isNotBlank(type)){
            if (StringUtils.isBlank(orderby)){
                orderby = "count desc , a.mid desc ";
            }
            if (limit < 1 || limit > Constants.MAX_POSTS){
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderby);
            paraMap.put("limit", limit);
            return metaMapper.selectMetaDtoByMap(paraMap);
        }
        return null;
    }
}
