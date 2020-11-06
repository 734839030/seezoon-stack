package com.seezoon.admin.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.admin.modules.sys.service.SysParamService;
import com.seezoon.framework.web.BaseController;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysParamController extends BaseController {

    private final SysParamService sysParamService;

}
