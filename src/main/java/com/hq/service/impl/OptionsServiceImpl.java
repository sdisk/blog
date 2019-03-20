package com.hq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hq.dao.OptionsMapper;
import com.hq.model.Options;
import com.hq.service.OptionsService;
import io.swagger.models.Operation;
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
public class OptionsServiceImpl extends ServiceImpl<OptionsMapper, Options> implements OptionsService {

    @Override
    public Operation getOptionByName(String site_record) {
        return null;
    }

    @Override
    public List<Options> getOptions() {
        return null;
    }
}
