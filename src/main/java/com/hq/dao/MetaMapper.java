package com.hq.dao;

import com.hq.entity.Meta;
import java.util.List;

public interface MetaMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Meta record);

    Meta selectByPrimaryKey(Integer mid);

    List<Meta> selectAll();

    int updateByPrimaryKey(Meta record);
}