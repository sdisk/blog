package com.hq.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by huang on 19/3/2019.
 */
@Setter
@Getter
@TableName("t_meta")
public class Meta extends Model<Meta>
{
    private static final long serialVersionUID = 7958014447426554919L;
    /**
     * 项目主键
     */
    private Integer mid;
    /**
     * 名称
     */
    private String name;
    /**
     * 项目缩略名
     */
    private String slug;
    /**
     * 项目类型
     */
    private String type;
    /**
     * 对于的文章类型
     */
    private String contentType;
    /**
     * 选项描述
     */
    private String description;
    /**
     * 项目排序
     */
    private Integer sort;

    private Integer parentId;

    @Override
    protected Serializable pkVal() {
        return this.mid;
    }
}
