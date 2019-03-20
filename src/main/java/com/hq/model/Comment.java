package com.hq.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by huang on 19/3/2019.
 */
@Getter
@Setter
@TableName("t_comment")
public class Comment extends Model<Comment>
{
    private static final long serialVersionUID = -1766761827879578447L;
    /**
     * 主键
     */
    @TableId(value = "coid",type = IdType.AUTO)
    private Integer coid;
    /**
     * 关联的文章表主键
     */
    private Integer cid;

    private Date createTime;
    /**
     * 评论者
     */
    private String author;
    /**
     * 评论所属用户id
     */
    private Integer authorId;
    /**
     * 评论所属内容作者id
     */
    private Integer ownerId;
    /**
     * 评论者邮件
     */
    private String mail;
    /**
     * 评论这网址
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
     * 评论状态
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

    @Override
    protected Serializable pkVal() {
        return this.coid;
    }
}

