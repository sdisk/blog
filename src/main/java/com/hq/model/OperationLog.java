package com.hq.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @description: 操作日志记录
 * @author: Mr.Huang
 * @create: 2019-03-19 11:20
 **/
@Setter
@Getter
@TableName("t_operation_log")
public class OperationLog extends Model<OperationLog> {
    private static final long serialVersionUID = 2420978397159524867L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 用户id
     */
    private Integer userid;
    /**
     * 类名称
     */
    private String className;
    /**
     *方法名称
     */
    private String method;
    /**
     * 产生的数据
     */
    private String data;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否成功
     */
    private String succeed;
    /**
     * 备注
     */
    private String message;
}
