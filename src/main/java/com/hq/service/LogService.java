package com.hq.service;

/**
 * @description: 用户请求日志
 * @author: Mr.Huang
 * @create: 2019-03-19 10:47
 **/
public interface LogService {

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
