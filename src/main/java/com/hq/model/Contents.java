package com.hq.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @description: 文章内容
 * @author: Mr.Huang
 * @create: 2019-03-19 17:03
 **/
@Setter
@Getter
@TableName("t_content")
public class Contents extends Model<Contents> {

    private static final long serialVersionUID = -337266141919123755L;

    /**
     * 文章的主键编号
     */
    @TableId(value = "id",type = IdType.AUTO)
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
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 内容更改时间
     */
    private Timestamp modifyTime;
    /**
     * 内容文字
     */
    private String content;
    /**
     * 内容所属用户id
     */
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
     * 点击次数
     */
    private Integer hits;
    /**
     * 内容所属评论数
     */
    private Integer commentsNum;
    /**
     * 是否允许评论
     */
    private Integer allowComment;
    /**
     * 是否允许ping
     */
    private Integer allowPing;
    /**
     * 允许出现在聚合中
     */
    private Integer allowFeed;
    /**
     * 允许可见
     */
    private Integer allowShow;

    @Override
    protected Serializable pkVal() {
        return this.cid;
    }
}