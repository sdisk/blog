package com.hq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hq.dao.OperationLogMapper;
import com.hq.model.OperationLog;
import com.hq.service.OperationLogService;
import com.hq.service.OptionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-02 10:13
 **/
@Service
@Slf4j
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {
}
