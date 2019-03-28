package com.hq.utils;

import com.hq.common.rest.Result;

/**
 * Created by huang on 17/3/2019.
 */
public class ResultUtil
{
    private static final Integer CODE_SUCCESS = 200;
    private static final Integer CODE_FAIL = 400;
    private static final String MSG_SUCCESS = "success";
    private static final String MSG_FAIL = "fail";

    public static Result success(Object object){
        Result result = new Result();
        result.setCode(CODE_SUCCESS);
        result.setMsg(MSG_SUCCESS);
        result.setData(object);
        return result;
    }
    public static Result success(){
        return success(null);
    }
    public static Result fail(){
        Result result = new Result();
        result.setCode(CODE_FAIL);
        result.setMsg(MSG_FAIL);
        return result;
    }
    public static Result fail(String msg){
        Result result = new Result();
        result.setCode(CODE_FAIL);
        result.setMsg(msg);
        return result;
    }
    public static Result fail(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
