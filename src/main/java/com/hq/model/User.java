package com.hq.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
public class User implements Serializable
{

    private static final long serialVersionUID = 6204845789383511114L;

    private Integer uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 主页地址
     */
    private String homeUrl;

    /**
     * 用户显示名
     */
    private String screenName;

    /**
     * 用户创建时间
     */
    private Timestamp created;

    /**
     * 用户最后活跃时间
     */
    private Timestamp activated;

    /**
     * 用户最后登录时间
     */
    private Timestamp logged;

    /**
     * 用户组
     */
    private String groupName;

}