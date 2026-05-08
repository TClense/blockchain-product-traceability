package com.qhx.back.enums;

/**
 * @author: 本郡主是喵
 * @date: 2024-11-01 22:36
 **/
public enum RoleType
{
    PRODUCER("1","生产商"),
    DISTRIBUTOR("2","供应商"),
    RETAILER("3","零售商");

    private final String code;
    private final String desc;

    RoleType(String code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public String getCode()
    {
        return code;
    }

    public String getDesc()
    {
        return desc;
    }
}
