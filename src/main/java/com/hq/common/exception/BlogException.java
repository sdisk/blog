package com.hq.common.exception;


/**
 * @program: blog
 * @description: 业务异常
 * @author: Mr.Huang
 * @create: 2019-03-18 16:16
 **/
public class BlogException extends RuntimeException {
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BlogException(BlogExceptionEnum blogExceptionEnum) {
        this.code = blogExceptionEnum.getCode();
        this.message = blogExceptionEnum.getMessage();
    }
}
