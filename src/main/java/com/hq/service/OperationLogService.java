package com.hq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hq.model.OperationLog;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-02 10:12
 **/
public interface OperationLogService extends IService<OperationLog> {
    List<OperationLog> getLogs(int logNum);
}
