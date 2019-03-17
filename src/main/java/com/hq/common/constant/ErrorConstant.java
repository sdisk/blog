package com.hq.common.constant;

/**
 * Created by huang on 17/3/2019.
 */
public interface ErrorConstant
{
    interface Common{
        static final String PARAM_IS_EMPTY="参数为空";
        static final String PARAM_IS_ERROR="参数错误";
        static final String INVALID_PARAM="无效的参数";
        static final String CAN_NOT_FIND_PARAM_TO_CONTIUNE="找不到参数进行运行";
    }
    interface Config{
        static final String DELETE_CONFIG_FAIL = "删除配置失败";
        static final String UPDATE_CONFIG_FAIL = "更新配置失败";
    }
}
