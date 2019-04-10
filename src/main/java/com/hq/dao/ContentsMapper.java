package com.hq.dao;

import com.hq.entity.Contents;
import java.util.List;

public interface ContentsMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Contents record);

    Contents selectByPrimaryKey(Integer cid);

    List<Contents> selectAll();

    int updateByPrimaryKey(Contents record);
}