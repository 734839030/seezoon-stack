package com.seezoon.admin.modules.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.admin.modules.sys.dto.UserInfoVo;
import com.seezoon.admin.modules.sys.security.SecurityUtils;
import com.seezoon.admin.modules.sys.security.UserService;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "个人用户信息")
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getInfo")
    public Result<UserInfoVo> getUserInfo() {
        UserInfoVo userInfoVo = new UserInfoVo();
        Integer userId = SecurityUtils.getUserId();
        userService.findRolesByUserId(userId);
        return Result.ok(userInfoVo);
    }

}
