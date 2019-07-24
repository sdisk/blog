package com.hq.service.impl;

import com.hq.dao.OperationLogMapper;
import com.hq.model.OperationLog;
import com.hq.service.OperationLogService;
import com.qiniu.processing.OperationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-02 10:13
 **/
@Service
@Slf4j
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public List<OperationLog> getLogs(int logNum) {
        return operationLogMapper.getByNum(logNum);
    }
}
