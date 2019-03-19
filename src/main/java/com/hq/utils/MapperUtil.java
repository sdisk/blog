package com.hq.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hq.common.SpringContextHolder;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-19 14:44
 **/
public class MapperUtil<T> {

    /**
     * 包装的Mapper接口,clazz就是接口的类类型
     */
    private Class<T> clazz;

    private BaseMapper<?> baseMapper;

    private MapperUtil(Class<T> clazz) {
        this.clazz = clazz;
        this.baseMapper = (BaseMapper<?>) SpringContextHolder.getBean(clazz);
    }
    /**
     * 获取mapper
     */
    public BaseMapper<?> getMapper() {
        return this.baseMapper;
    }

    public static <T> T getMapper(Class <T> clazz){
        return SpringContextHolder.getBean(clazz);
    }
}
