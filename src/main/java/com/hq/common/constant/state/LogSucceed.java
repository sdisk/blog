package com.hq.common.constant.state;

/**
 * @author: Mr.Huang
 * @create: 2019-03-19 13:51
 **/
public enum LogSucceed {

    ALL(0,null),
    SUCCESS(1,"成功"),
    FAIL(2,"失败");

    Integer code;
    String message;

    LogSucceed(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
