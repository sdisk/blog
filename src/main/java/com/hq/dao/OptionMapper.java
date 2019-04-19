package com.hq.dao;

import com.hq.model.Option;

import java.util.List;

public interface OptionMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Option record);

    Option selectByPrimaryKey(Integer id);

    List<Option> selectAll();

    int updateByPrimaryKey(Option record);

    void updateOptionByName(Option option);

    void deleteOptionByName(String name);

    Option getOptionByName(String name);

    List<Option> getOptions();
}