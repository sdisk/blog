package com.hq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.dao.AttachMapper;
import com.hq.dto.AttachDto;
import com.hq.model.Attach;
import com.hq.service.AttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang on 29/4/2019.
 */
@Slf4j
@Service
public class AttachServiceImpl implements AttachService
{
    @Autowired
    private AttachMapper attachMapper;

    @Override
    @Cacheable(value = "attCaches", key = "'atts'+#p0")
    public PageInfo<AttachDto> getAtts(int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<AttachDto> atts = attachMapper.getAtts();
        PageInfo<AttachDto> pageInfo = new PageInfo<>(atts);
        return pageInfo;
    }

    @Override
    @CacheEvict(value={"attCaches", "attCache"}, allEntries=true, beforeInvocation=true)
    public void addAttAch(Attach attach) {
        if (null == attach){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        attachMapper.addAttAch(attach);
    }

    @Override
    @Cacheable(value = "attCache", key = "'attAchById' + #p0")
    public AttachDto getAttAchById(Integer id)
    {
        if (null == id){
            throw new BlogException(BlogExceptionEnum.FILE_NOT_FOUND);
        }
        return attachMapper.getAttAchById(id);
    }

    @Override
    @CacheEvict(value={"attCaches","attCache"},allEntries=true,beforeInvocation=true)
    public void deleteAttAch(Integer id)
    {
        if (null == id){
            throw new BlogException(BlogExceptionEnum.FILE_NOT_FOUND);
        }
        attachMapper.deleteAttAch(id);
    }
}
