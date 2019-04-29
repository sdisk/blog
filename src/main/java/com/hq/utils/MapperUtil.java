package com.hq.utils;

import com.hq.common.SpringContextHolder;

/**
 * @author: Mr.Huang
 * @create: 2019-03-19 14:44
 **/
public class MapperUtil<T> {

    /**
     * 包装的Mapper接口,clazz就是接口的类类型
     */
    private Class<T> clazz;


    private MapperUtil(Class<T> clazz) {
        this.clazz = clazz;
    }
    /**
     * 获取mapper
     */

    public static <T> T getMapper(Class <T> clazz){
        return SpringContextHolder.getBean(clazz);
    }
}
