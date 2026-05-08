package com.qhx.back.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qhx.back.model.vo.ResultVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 公共响应类
 *
 * @author: qhx20040819
 * @date: 2023-10-20 14:57
 **/


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // 异常不需要data字段,查询成功不需要,mes字段
public class Result
{

    private Object data;
    private String mes;
    private Integer code;


    // 成功返回
    public static   Result success(String _mes)
    {
        return new Result(null, _mes, 200);
    }


    // 成功返回，需要提示信息
    public static <T> Result success(String _mes, T data)
    {
        return new Result(data, _mes, 200);
    }

    // 成功返回，不需要提示信息
    public static <T> Result success(T data)
    {
        return new Result(data, null, 200);
    }

    public static <T> Result success(){
        return new Result(null, null, 200);
    }


    // 失败返回
    public static Result error(String _mes)
    {
        return new Result(null, _mes, 400);
    }

    // 这个对于失败返回(常见错误等统一封装用这个)
    public static Result error(ResultVO resultVO)
    {
        return new Result(resultVO);
    }

    public Result(ResultVO resultVO)
    {
        this.data = null;
        this.mes = resultVO.getMes();
        this.code = resultVO.getCode();
    }


}
