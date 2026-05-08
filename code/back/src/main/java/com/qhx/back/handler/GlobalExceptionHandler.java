package com.qhx.back.handler;

import com.qhx.back.exception.WeBaseFrontException;
import com.qhx.back.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 *
 * @author: qhx20040819
 * @date: 2023-10-20 16:19
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler
{

    @ExceptionHandler(WeBaseFrontException.class)
    public Result handlerWebaseFrontException(WeBaseFrontException excepxtion)
    {
        log.error("webase-front exception:" , excepxtion);
        // 问题
        return Result.error(excepxtion.mes);
    }


    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception excepxtion)
    {
        log.error("Exception：",excepxtion);
        return Result.error(excepxtion.getMessage());
    }
}





