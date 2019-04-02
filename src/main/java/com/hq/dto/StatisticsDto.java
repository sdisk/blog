package com.hq.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-02 10:08
 **/
@Getter
@Setter
public class StatisticsDto {

    /**
     * 文章数
     */
    private Long articles;

    /**
     * 评论数
     */
    private Long comments;

    /**
     * 连接数
     */
    private Long links;

    /**
     * 附件数
     */
    private Long attachs;
}
