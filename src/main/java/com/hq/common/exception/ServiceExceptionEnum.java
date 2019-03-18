package com.hq.common.exception;

/**
 * @author: Mr.Huang
 * @create: 2019-03-18 16:10
 **/
public interface ServiceExceptionEnum {

    /**
     * 获取异常编码
     */
    Integer getCode();
    /**
     * 获取异常信息
     */
    String getMessage();
}
