package com.hq.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-19 16:42
 **/
@Getter
@Setter
@TableName("t_user")
public class User extends Model<User> {

    private static final long serialVersionUID = -6907457327025809937L;

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    private String userName;

    private String password;

    private String eamil;

    /**
     * 主页地址
     */
    private String homeUrl;
    /**
     * 用户显示的名称
     */
    private String screenName;
    /**
     * 用户注册时间
     */
    private Timestamp created;
    /**
     * 最后活动时间
     */
    private Timestamp activated;
    /**
     * 上次登录最后活跃时间
     */
    private Timestamp logged;
    /**
     * 用户组
     */
    private String groupName;

    @Override
    protected Serializable pkVal() {
        return this.uid;
    }
}
