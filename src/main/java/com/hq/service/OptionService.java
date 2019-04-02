package com.hq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hq.model.Option;

import java.util.List;

/**
 * Created by huang on 19/3/2019.
 */
public interface OptionService extends IService<Option>
{
    Option getOptionByName(String site_record);

    List<Option> getOptions();
}
