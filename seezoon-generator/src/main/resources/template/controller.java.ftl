package com.seezoon.admin.modules.${moduleName}.web;

import java.io.Serializable;
<#if hasRichText>
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
</#if>
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.seezoon.boot.common.web.BaseController;
import com.seezoon.boot.context.dto.ResponeModel;
import com.seezoon.service.modules.${moduleName}.entity.${className};
import com.seezoon.service.modules.${moduleName}.service.${className}Service;
<#if hasFileUpload >
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.seezoon.service.modules.sys.entity.SysFile;
import com.seezoon.service.modules.sys.service.SysFileService;
import com.seezoon.boot.common.utils.FileUtils;
import com.seezoon.boot.common.file.handler.FileHandler;
import com.seezoon.service.modules.sys.dto.FileInfo;
import com.seezoon.boot.common.Constants;
</#if>

/**
 * ${menuName}controller
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf ${.now}
 */
@RestController
@RequestMapping("${r'${admin.path}'}/${moduleName}/${functionName}")
public class ${className}Controller extends BaseController {

	@Autowired
	private ${className}Service ${className?uncap_first}Service;
	<#if hasFileUpload >
	@Autowired
	private SysFileService sysFileService;
	@Autowired
	private FileHandler fileHandler;
	</#if>

	@PreAuthorize("hasAuthority('${moduleName}:${functionName}:qry')")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(${className} ${className?uncap_first}) {
		PageInfo<${className}> page = ${className?uncap_first}Service.findByPage(${className?uncap_first}, ${className?uncap_first}.getPage(), ${className?uncap_first}.getPageSize());
		return ResponeModel.ok(page);
	}
	@PreAuthorize("hasAuthority('${moduleName}:${functionName}:qry')")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam ${pkType} id) {
		${className} ${className?uncap_first} = ${className?uncap_first}Service.findById(id);
		 <#-- 富文本处理 -->
		<#list columnInfos as columnInfo>
		<#if columnInfo.inputType! == "richtext">
        if (null != ${className?uncap_first}) {
		    if (StringUtils.isNotEmpty(${className?uncap_first}.get${columnInfo.javaFieldName?cap_first}())) {
				${className?uncap_first}.set${columnInfo.javaFieldName?cap_first}(StringEscapeUtils.unescapeHtml4(${className?uncap_first}.get${columnInfo.javaFieldName?cap_first}()));
			}
		}
        </#if>
        <#if columnInfo.inputType! == "file" || columnInfo.inputType! == "picture">
        	 <#-- 文件处理 -->
        	if (StringUtils.isNotEmpty(${className?uncap_first}.get${columnInfo.javaFieldName?cap_first}())) {
        		String[] ${columnInfo.javaFieldName}s = StringUtils.split(${className?uncap_first}.get${columnInfo.javaFieldName?cap_first}(), Constants.SEPARATOR);
        		List<FileInfo> ${columnInfo.javaFieldName}Array = new ArrayList<>();
	        	for (String path :${columnInfo.javaFieldName}s) {
	        		SysFile sysFile = sysFileService.findById(FileUtils.getFileId(path));
	        		if (null != sysFile) {
	        			${columnInfo.javaFieldName}Array.add(new FileInfo(fileHandler.getFullUrl(path),path,sysFile.getName()));
	        		}
	        	}
	        	${className?uncap_first}.set${columnInfo.javaFieldName?cap_first}Array(${columnInfo.javaFieldName}Array);
        }
		 </#if>
   		</#list>
		return ResponeModel.ok(${className?uncap_first});
	}
	@PreAuthorize("hasAuthority('${moduleName}:${functionName}:save')")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated ${className} ${className?uncap_first}, BindingResult bindingResult) {
		int cnt = ${className?uncap_first}Service.save(${className?uncap_first});
		return ResponeModel.ok(cnt);
	}
	@PreAuthorize("hasAuthority('${moduleName}:${functionName}:update')")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated ${className} ${className?uncap_first}, BindingResult bindingResult) {
		int cnt = ${className?uncap_first}Service.updateSelective(${className?uncap_first});
		return ResponeModel.ok(cnt);
	}
	@PreAuthorize("hasAuthority('${moduleName}:${functionName}:delete')")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = ${className?uncap_first}Service.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
