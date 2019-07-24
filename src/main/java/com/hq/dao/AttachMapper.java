package com.hq.dao;

import com.hq.dto.AttachDto;
import com.hq.model.Attach;

import java.util.List;

public interface AttachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attach record);

    Attach selectByPrimaryKey(Integer id);

    List<Attach> selectAll();

    int updateByPrimaryKey(Attach record);

    Long getAttachCount();

    void addAttAch(Attach attach);

    List<AttachDto> getAtts();

    AttachDto getAttAchById(Integer id);

    void deleteAttAch(Integer id);
}