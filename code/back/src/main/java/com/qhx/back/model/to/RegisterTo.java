package com.qhx.back.model.to;

import lombok.Data;

/**
 * @author: 本郡主是喵
 * @date: 2024-11-01 22:33
 **/
@Data
public class RegisterTo
{
    private String address; // 注册地址
    private String role;
    private String roleAddress; // 邀请账户地址：已经注册的角色地址
}
