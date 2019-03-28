package com.hq.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @description: 登录记录
 * @author: Mr.Huang
 * @create: 2019-03-19 10:50
 **/
@Setter
@Getter
@TableName("t_login_log")
public class LoginLog extends Model<LoginLog> {

    private static final long serialVersionUID = 4341232647071899080L;

    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
//    /**
//     * 日志类型
//     */
//    private String logType;
    /**
     * 日志名称
     */
    private String logName;
    /**
     * 管理员id
     */
    private Integer userId;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 是否执行成功
     */
    private String succeed;
    /**
     * 登录者ip
     */
    private String ip;
    /**
     * 备注
     */
    private String message;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
