package com.hq.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class Contents implements Serializable
{
    private static final long serialVersionUID = -3121979221729526368L;

    private Integer cid;

    /**
     * 内容标题
     */
    private String title;

    /**
     * 标题图片
     */
    private String titlePic;

    /**
     * 内容缩略名
     */
    private String slug;

    /**
     * 文章内容
     */
    private String content;

    private Timestamp createTime;

    private Timestamp modifyTime;

    private Integer authorId;

    /**
     * 内容类别
     */
    private String type;

    /**
     * 内容状态
     */
    private String status;

    /**
     * 标签列表
     */
    private String tags;

    /**
     * 分类列表
     */
    private String categories;

    /**
     * 点击数
     */
    private Integer hits;

    /**
     * 内容评论数
     */
    private Integer commentsNum;

    /**
     * 是否允许评论
     */
    private Integer allowComment;

    /**
     * 是否ping
     */
    private Integer allowPing;

    /**
     * 是否允许出现在聚合中
     */
    private Integer allowFeed;

    /**
     * 是否公开可见
     */
    private Integer allowShow;

}