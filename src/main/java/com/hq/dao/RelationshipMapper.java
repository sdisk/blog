package com.hq.dao;

import com.hq.model.Relationship;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RelationshipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Relationship record);

    Relationship selectByPrimaryKey(@Param("id") Integer id);

    List<Relationship> selectAll();

    int updateByPrimaryKey(Relationship record);

    Long getCountById(@Param("cid") Integer cid,@Param("mid") int mid);

    List<Relationship> getRelationShipByMid(@Param("mid") Integer mid);

    List<Relationship> getRelationShipByCid(@Param("cid") Integer cid);

    void deleteByMid(@Param("mid") Integer mid);

    void deleteByCid(int cid);
}