package com.hq.dto;

import com.hq.model.Meta;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 标签、分类
 * @author: Mr.Huang
 * @create: 2019-03-20 23:13
 **/
@Setter
@Getter
public class MetaDto extends Meta {

    private int count;
}
