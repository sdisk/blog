package com.hq.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-17 17:19
 **/
@Data
public class AttachDto implements Serializable{

    private static final long serialVersionUID = 2751740569332068033L;

    /** 主键编号 */
    private Integer id;
    /** 文件名称 */
    private String fname;
    /** 文件类型 */
    private String ftype;
    /** 文件的地址 */
    private String fkey;
    /** 创建人的id */
    private Integer creatorId;
    /** 创建的时间 */
    private Timestamp createTime;
    /** 用户名 */
    private String userName;
}
