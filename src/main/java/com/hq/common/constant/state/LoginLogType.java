package com.hq.common.constant.state;


/**
 * @author: Mr.Huang
 * @create: 2019-03-19 13:46
 **/
public enum LoginLogType {

    ALL(0,null), //全部登录日志
    LOGIN(1,"登录"),
    LOGIN_FAIL(2,"登录失败"),
    EXIT(3,"退出登录"),
    LOGIN_EXCEPTION(4,"登录异常"),
    UP_PASSWORD(5,"修改密码"),
    UP_PASSWORD_EXCEPTION(6,"修改密码异常");

    Integer code;
    String message;

    LoginLogType(Integer code, String message) {
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
