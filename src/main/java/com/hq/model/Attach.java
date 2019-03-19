package com.hq.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by huang on 19/3/2019.
 */
@Setter
@Getter
@TableName("t_attach")
public class Attach extends Model<Attach>
{
    /**
     * 主键编号
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 文件名称
     */
    private String fname;
    /**
     * 文件类型
     */
    private String ftype;
    /**
     * 文件地址
     */
    private String fkey;
    /**
     * 创建人的id
     */
    private Integer creatorId;
    /**
     * 创建的时间
     */
    private Date createTime;
}

