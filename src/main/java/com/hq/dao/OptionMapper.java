package com.hq.dao;

import com.hq.entity.Option;
import java.util.List;

public interface OptionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Option record);

    Option selectByPrimaryKey(Integer id);

    List<Option> selectAll();

    int updateByPrimaryKey(Option record);
}