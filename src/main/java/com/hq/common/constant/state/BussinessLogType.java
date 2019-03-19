package com.hq.common.constant.state;

/**
 * @author: Mr.Huang
 * @create: 2019-03-19 13:49
 **/
public enum BussinessLogType {

    ALL(0,null),
    SYS_SETTING(1,"保存系统设置"),
    INIT_SITE(2,"初始化站点"),
    BUSSINESS(3,"业务日志"),
    BUSSINESS_EXCEPTION(4,"业务异常日志");

    Integer code;
    String message;

    BussinessLogType(Integer code, String message) {
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
