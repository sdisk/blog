package com.hq.service;


import com.hq.model.Option;

import java.util.List;

/**
 * Created by huang on 19/3/2019.
 */
public interface OptionService
{
    Option getOptionByName(String site_record);

    List<Option> getOptions();
}
