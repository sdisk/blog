package com.hq.service;


import com.hq.model.OperationLog;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-02 10:12
 **/
public interface OperationLogService {
    List<OperationLog> getLogs(int logNum);
}
