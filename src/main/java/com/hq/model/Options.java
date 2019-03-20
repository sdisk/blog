package com.hq.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by huang on 19/3/2019.
 */
@Getter
@Setter
@TableName("t_options")
public class Options extends Model<Options>
{
    private static final long serialVersionUID = 6953189479922330735L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 内容
     */
    private String value;
    /**
     * 备注
     */
    private String description;

}
