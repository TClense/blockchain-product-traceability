package com.qhx.back.exception;

/**
 * webase-front异常
 *
 * @author: 本郡主是喵
 * @date: 2024-11-01 21:50
 **/

public class WeBaseFrontException extends RuntimeException
{
    public String mes;
    public WeBaseFrontException(Throwable cause)
    {
        super(cause);
    }

    public WeBaseFrontException(String message)
    {
        super(message);
        this.mes = message;
    }
}
