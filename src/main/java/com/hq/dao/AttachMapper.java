package com.hq.dao;

import com.hq.model.Attach;

import java.util.List;

public interface AttachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attach record);

    Attach selectByPrimaryKey(Integer id);

    List<Attach> selectAll();

    int updateByPrimaryKey(Attach record);

    Long getAttachCount();
}