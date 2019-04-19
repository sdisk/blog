package com.hq.service.impl;

import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.dao.OptionMapper;
import com.hq.model.Option;
import com.hq.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-20 16:50
 **/
@Service
@Slf4j
@CacheConfig(cacheNames = {"blogCache"})
public class OptionServiceImpl  implements OptionService {

    @Autowired
    private OptionMapper optionMapper;

    @Override
    @Cacheable(value = "optionCache", key = "'optionByName_'+#p0")
    public Option getOptionByName(String name) {
        if (StringUtils.isBlank(name)){
            throw  new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        return optionMapper.getOptionByName(name);
    }

    @Override
    @Cacheable(value = "optionCaches", key = "'option_'+#p0")
    public List<Option> getOptions() {

        return optionMapper.getOptions();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"optionCache", "optionCaches"}, allEntries = true, beforeInvocation = true)
    public void saveOptions(Map<String, String> querys) {
        if (null != querys && !querys.isEmpty()){
            querys.forEach(this :: updateOptionByName);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"optionCache", "optionCaches"}, allEntries = true, beforeInvocation = true)
    public void deleteOptionByName(String name) {
        if (StringUtils.isBlank(name)){
            throw  new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        optionMapper.deleteOptionByName(name);
    }

    @Transactional
    @CacheEvict(value = {"optionCache", "optionCaches"}, allEntries = true, beforeInvocation = true)
    public void updateOptionByName(String name, String value){
        if(StringUtils.isBlank(name)){
            throw  new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        Option option = new Option();
        option.setName(name);
        option.setValue(value);
        optionMapper.updateOptionByName(option);
    }
}
