package com.hq.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
public class OperationLog implements Serializable
{
    private static final long serialVersionUID = -8360214973406105195L;

    private Integer id;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 日志名称
     */
    private String logName;

    /**
     * 日志创建人
     */
    private Integer userId;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String method;

    /**
     * 产生数据
     */
    private String data;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 是否成功
     */
    private String succeed;

    /**
     * 产生日志的ip
     */
    private String ip;

    /**
     * 日志创建消息
     */
    private String messgae;

}