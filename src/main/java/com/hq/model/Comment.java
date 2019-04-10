package com.hq.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class Comment implements Serializable
{
    private static final long serialVersionUID = 2242242745862176502L;

    private Integer coid;

    /**
     * contents表主键
     */
    private Integer cid;

    private Timestamp created;

    /**
     * 评论者
     */
    private String author;

    /**
     * 评论者用户id
     */
    private Integer authorId;

    /**
     * 评论所属内容作者id
     */
    private Integer ownerId;

    /**
     * 评论者邮箱
     */
    private String mail;

    /**
     * 评论者地址
     */
    private String url;

    /**
     * 评论者ip地址
     */
    private String ip;

    /**
     * 评论者客户端
     */
    private String agent;

    /**
     * 评论类型
     */
    private String type;

    /**
     * 评论者状态
     */
    private String status;

    /**
     * 父级评论
     */
    private Integer parentId;

    /**
     * 评论内容
     */
    private String content;
}