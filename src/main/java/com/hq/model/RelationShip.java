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
@Setter
@Getter
@TableName("t_relationship")
public class RelationShip extends Model<RelationShip>
{
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *文章主键编号
     */
    private Integer cid;
    /**
     * 项目编号
     */
    private Integer mid;
}
