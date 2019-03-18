package com.hq.common.rest;


import lombok.Getter;
import lombok.Setter;

/**
 * 返回对象
 * Created by huang on 17/3/2019.
 */
@Getter
@Setter
public class Result<T>
{
    private Integer code;
    private String msg;
    private T data;
    private long timestamp;

    public Result()
    {
        this.timestamp = System.currentTimeMillis() / 1000;
    }
    public Result(Integer code)
    {
        this.code = code;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public Result(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public Result(Integer code, T data)
    {
        this.code = code;
        this.data = data;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

}
