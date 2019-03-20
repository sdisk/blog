package com.hq.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 文章查询条件
 * @author: Mr.Huang
 * @create: 2019-03-20 23:08
 **/
@Getter
@Setter
public class ContentQuery {

    /**
     * 标签
     */
    private String tag;
    /**
     * 类别
     */
    private String category;
    /**
     * 状态
     */
    private String status;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容匹配
     */
    private String content;
    /**
     * 文章类型
     */
    private String type;
    /**
     * 开始时间戳
     */
    private Integer startTime;
    /**
     * 结束时间戳
     */
    private Integer endTime;
}
