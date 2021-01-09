package com.seezoon.admin.modules.sys.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageSerializable;
import com.google.common.collect.Lists;
import com.seezoon.admin.framework.file.FileService;
import com.seezoon.admin.modules.sys.service.SysFileService;
import com.seezoon.dao.modules.sys.entity.SysFile;
import com.seezoon.dao.modules.sys.entity.SysFileCondition;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.component.file.FileInfo;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 文件
 *
 * @author seezoon-generator 2021年1月2日 上午1:04:41
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/sys/file")
@RequiredArgsConstructor
public class SysFileController extends BaseController {

    private final SysFileService sysFileService;
    private final FileService fileService;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:file:query')")
    @GetMapping("/query/{id}")
    public Result<SysFile> query(@PathVariable String id) {
        SysFile sysFile = sysFileService.find(id);
        return Result.ok(sysFile);
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:file:query')")
    @PostMapping("/query")
    public Result<PageSerializable<SysFile>> query(@Valid @RequestBody SysFileCondition condition) {
        PageSerializable<SysFile> pageSerializable =
            sysFileService.find(condition, condition.getPage(), condition.getPageSize());
        pageSerializable.getList().forEach((v) -> {
            v.setUrl(this.fileService.getUrl(v.getRelativePath()));
        });
        return Result.ok(pageSerializable);
    }

    @ApiOperation(value = "单个上传")
    @PreAuthorize("hasAuthority('sys:file:upload')")
    @PostMapping(value = "/upload")
    public Result<FileInfo> upload(@NotNull @RequestParam MultipartFile file) throws IOException {
        FileInfo fileInfo = sysFileService.upload(file.getOriginalFilename(), file.getContentType(), file.getSize(),
            file.getInputStream());
        return Result.ok(fileInfo);
    }

    @ApiOperation(value = "批量上传")
    @PreAuthorize("hasAuthority('sys:file:upload')")
    @PostMapping(value = "/uploadBatch")
    public Result<List<FileInfo>> upload(@NotEmpty @RequestParam MultipartFile[] files) {
        List<FileInfo> fileInfos = Lists.newArrayList();
        Arrays.stream(files).forEach((file) -> {
            try {
                FileInfo fileInfo = sysFileService.upload(file.getOriginalFilename(), file.getContentType(),
                    file.getSize(), file.getInputStream());
                fileInfos.add(fileInfo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return Result.ok(fileInfos);
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:file:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam String id) {
        sysFileService.delete(id);
        return Result.SUCCESS;
    }
}
