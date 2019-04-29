package com.hq.service.impl;

import com.github.pagehelper.PageInfo;
import com.hq.dao.AttachMapper;
import com.hq.dto.AttachDto;
import com.hq.service.AttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageInfo<AttachDto> getAtts(int page, int limit)
    {
        return null;
    }
}
