package com.hq.common.exception;

/**
 * @author: Mr.Huang
 * @create: 2019-03-18 16:12
 **/
public enum BlogExceptionEnum implements ServiceExceptionEnum{

    /**
     * 文件上传
     */
    FILE_READING_ERROR(400, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(400, "FILE_NOT_FOUND!"),

    /**
     * 错误的请求
     */
    REQUEST_NULL(400, "请求有错误"),
    SERVER_ERROR(500, "服务器异常"),

    /**
     * 登录错误请求
     */
    USERNAME_PASSWORD_ENPTY(400, "用户名或密码为空"),
    USERNAME_PASSWORD_ERROR(400, "用户名不存在或密码错误"),
    USER_NO_LOGIN(400, "用户未登录"),

    /**
     * 请求参数
     */
    PARAM_IS_EMPTY(400, "参数为空"),
    PARAM_INVALID_PARAM(400, "参数无效"),

    USER_ID_ISNULL(400,"用户编号不能为空！"),

    ;
    private Integer code;
    private String message;

    BlogExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
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
}
