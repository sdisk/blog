package com.hq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hq.model.Options;
import io.swagger.models.Operation;

import java.util.List;

/**
 * Created by huang on 19/3/2019.
 */
public interface OptionsService extends IService<Options>
{
    Operation getOptionByName(String site_record);

    List<Options> getOptions();
}
