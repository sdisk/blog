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

    public Result()
    {
    }

    public Result(Integer code)
    {
        this.code = code;
    }

    public Result(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, T data)
    {
        this.code = code;
        this.data = data;
    }

}
