package com.qhx.back.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: qhx20040819
 * @date: 2023-10-20 15:00
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO
{

    private String mes;
    private Integer code;
    public static final ResultVO PARAMETER_MISS = new ResultVO("参数确实", 40401);
    public static final ResultVO USER_NOT_EXISTS = new ResultVO("用户不存在", 40402);
    public static final ResultVO CONTRACT_REQUEST_ERROR = new ResultVO("智能合约请求出错", 40403);
}
