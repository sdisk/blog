package com.hq.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Option implements Serializable{
    private static final long serialVersionUID = 4407096738363869099L;

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
     * 描述
     */
    private String description;

}