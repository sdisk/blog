package com.hq.handler;

import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.common.rest.Result;
import com.hq.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 统一异常处理
 * @author: Mr.Huang
 * @create: 2019-03-18 16:19
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BlogException.class)
    @ResponseBody
    public Result blogException(BlogException e){
        log.error("业务异常:{}",e);
        return ResultUtil.fail(e.getCode(), e.getMessage());
    }
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Result runtimeException(RuntimeException e){
        log.error("运行时异常:{}",e);
        e.printStackTrace();
        return ResultUtil.fail(BlogExceptionEnum.SERVER_ERROR.getCode(), BlogExceptionEnum.SERVER_ERROR.getMessage());
    }
}
