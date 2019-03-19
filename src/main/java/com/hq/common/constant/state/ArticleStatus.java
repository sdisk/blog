package com.hq.common.constant.state;

/**
 * @author: Mr.Huang
 * @create: 2019-03-19 13:54
 **/
public enum ArticleStatus {

    DRAFT(1,"草稿"),
    PUBLISH(2,"发布");

    Integer code;
    String message;

    ArticleStatus(Integer code, String message) {
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
