package com.hq.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Relationship implements Serializable
{
    private static final long serialVersionUID = -2168591279925987893L;

    private Integer id;

    /**
     * 文章主键编号
     */
    private Integer cid;

    /**
     * 项目编号
     */
    private Integer mid;

}