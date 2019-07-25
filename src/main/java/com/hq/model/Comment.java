package com.hq.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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

    private Timestamp createTime;

    /**
     * 评论者
     */
    private String author;

    /**
     * 评论者头像
     */
    private String gavatar;

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

    /**
     * 是否是作者评论 0:不是 1:是
     */
    private Integer isAdmin;

    /**
     * 当前评论下的所有子评论
     */
    @Transient
    private List<Comment> childComments;
}