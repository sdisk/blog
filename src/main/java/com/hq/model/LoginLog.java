package com.hq.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class LoginLog implements Serializable
{
    private static final long serialVersionUID = -4239214059185713074L;

    private Integer id;

    /**
     * 日志名称
     */
    private String logName;

    /**
     * 日志创建人
     */
    private Integer userId;

    /**
     * 日志创建时间
     */
    private Timestamp createTime;

    /**
     * 日志创建是否成功
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