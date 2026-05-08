package com.qhx.back.contreoller;

import com.qhx.back.model.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author: 本郡主是喵
 * @date: 2025-01-03 14:56
 **/
@RestController
public class OwnerController
{
    @Value("${contract.owner}")
    private String owner;

    @GetMapping("/getContractOwner")
    public Result getContractOwner()
    {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("owner", owner);
        return Result.success(hashMap);
    }
}
