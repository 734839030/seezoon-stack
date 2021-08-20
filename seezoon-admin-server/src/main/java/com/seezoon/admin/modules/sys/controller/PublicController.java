package com.seezoon.admin.modules.sys.controller;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.admin.modules.sys.service.SysUserService;
import com.seezoon.dao.framework.constants.EntityStatus;
import com.seezoon.dao.modules.sys.entity.SysUser;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @author hdf
 */
@Api(tags = "无需登录服务")
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController extends BaseController {

    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "小程序注册")
    @PostMapping("/mp_regist")
    public Result mpRegist(@NotEmpty String code, @NotEmpty String encryptedData, @NotEmpty String iv)
        throws WxErrorException {
        WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.jsCode2SessionInfo(code);
        WxMaPhoneNumberInfo phoneNoInfo =
            wxMaService.getUserService().getPhoneNoInfo(wxMaJscode2SessionResult.getSessionKey(), encryptedData, iv);
        final String phoneNumber = phoneNoInfo.getPhoneNumber();
        if (StringUtils.isEmpty(phoneNumber)) {
            return Result.error("当前手机号为空，无法登录，请完善手机号");
        }
        SysUser sysUser = sysUserService.findByMobile(phoneNumber);
        if (null == sysUser) { // 注册
            sysUser = new SysUser();
            sysUser.setUsername(phoneNumber);
            sysUser.setMobile(phoneNumber);
            sysUser.setName(phoneNumber);
            sysUser.setOpenId(wxMaJscode2SessionResult.getOpenid());
            sysUser.setUnionId(wxMaJscode2SessionResult.getUnionid());
            sysUser.setStatus(EntityStatus.NORMAL.status());
            sysUserService.save(sysUser);
        } else {
            sysUser.setOpenId(wxMaJscode2SessionResult.getOpenid());
            sysUser.setUnionId(wxMaJscode2SessionResult.getUnionid());
            sysUserService.updateSelective(sysUser);
        }
        return Result.ok();
    }
}
