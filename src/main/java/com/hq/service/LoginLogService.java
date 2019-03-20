package com.hq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hq.model.LoginLog;

/**
 * @description: 用户请求日志
 * @author: Mr.Huang
 * @create: 2019-03-19 10:47
 **/
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 添加日志
     * @param action
     * @param data
     * @param ip
     * @param authorId
     */
    void addLog(String action, String data, String ip, Integer authorId);

    /**
     * 删除日志
     * @param id
     */
    void deleteLogById(Integer id);
}
