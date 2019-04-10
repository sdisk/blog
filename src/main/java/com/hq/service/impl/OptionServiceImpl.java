package com.hq.service.impl;

import com.hq.model.Option;
import com.hq.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-20 16:50
 **/
@Service
@Slf4j
public class OptionServiceImpl  implements OptionService {

    @Override
    public Option getOptionByName(String site_record) {
        return null;
    }

    @Override
    public List<Option> getOptions() {
        return null;
    }
}
