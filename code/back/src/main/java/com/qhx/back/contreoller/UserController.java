package com.qhx.back.contreoller;

import cn.hutool.json.JSONArray;
import com.qhx.back.context.AddressContext;
import com.qhx.back.model.Result;
import com.qhx.back.model.to.LoginTo;
import com.qhx.back.model.to.RegisterTo;
import com.qhx.back.model.to.UserTo;
import com.qhx.back.util.HttpUtil;
import com.qhx.back.util.UserAddressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@Api(tags = "用户接口")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HttpUtil httpUtil;

    // 1. 用户登录
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@RequestBody LoginTo loginTo) {

        if (!UserAddressUtil.isLegalAddress(loginTo.getAddress())) {
            return Result.error("登录地址不合法");
        }

        JSONArray result = httpUtil.call(
                "getUser",
                Arrays.asList(loginTo.getAddress())
        );

        if (result == null || result.size() < 3) {
            return Result.error("用户不存在");
        }

        Boolean isRegister = result.getBool(2);

        if (!isRegister) {
            return Result.error("用户未注册");
        }

        AddressContext.setAddress(loginTo.getAddress());

        return Result.success();
    }

    // 2. 用户注册
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(@RequestBody RegisterTo registerTo) {

        if (!UserAddressUtil.isLegalAddress(registerTo.getAddress())) {
            return Result.error("注册地址不合法");
        }

        if (!UserAddressUtil.isLegalAddress(registerTo.getRoleAddress())) {
            return Result.error("角色地址不合法");
        }

        httpUtil.sendTransaction(
                registerTo.getAddress(),
                "setUser",
                Arrays.asList(
                        String.valueOf(registerTo.getRole()),
                        registerTo.getRoleAddress()
                )
        );

        return Result.success();
    }

    // 3. 撤销用户
    @PostMapping("/cancelUser")
    @ApiOperation("撤销用户")
    public Result cancelUser(@RequestBody UserTo userTo) {

        if (!UserAddressUtil.isLegalAddress(userTo.getAddress())) {
            return Result.error("撤销地址不合法");
        }

        String address = AddressContext.getAddress();

        httpUtil.sendTransaction(
                address,
                "unsetUser",
                Arrays.asList(userTo.getAddress())
        );

        return Result.success();
    }

    // 4. 获取用户信息
    @GetMapping("/getUser")
    @ApiOperation("获取用户信息")
    public Result getUser(@RequestParam String address) {

        if (!UserAddressUtil.isLegalAddress(address)) {
            return Result.error("查询地址不合法");
        }

        JSONArray result = httpUtil.call("getUser",
                Arrays.asList(address));

        UserTo userTo = new UserTo();

        userTo.setAddress(result.getStr(0));
        userTo.setRole(result.getStr(1));

        return Result.success(userTo);
    }
}