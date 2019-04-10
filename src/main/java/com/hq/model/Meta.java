package com.hq.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Meta implements Serializable
{
    private static final long serialVersionUID = -2070569677449541580L;

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
     * 对应的文章类型
     */
    private String contentType;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目排序
     */
    private Integer sort;

    private Integer parentId;

}