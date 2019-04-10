package com.hq.dao;

import com.hq.model.Relationship;

import java.util.List;

public interface RelationshipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Relationship record);

    Relationship selectByPrimaryKey(Integer id);

    List<Relationship> selectAll();

    int updateByPrimaryKey(Relationship record);
}