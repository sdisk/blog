package com.hq.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-10 10:08
 **/
@Getter
@Setter
public class CommentQuery {
    /**
     * 状态
     */
    private String status;
    /**
     * 开始时间
     */
    private Timestamp startTime;
    /**
     * 结束时间
     */
    private Timestamp endTime;
    /**
     * 父评论编号
     */
    private Integer parent;

}
