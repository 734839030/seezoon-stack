package com.seezoon.admin.modules.sys.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.admin.framework.file.FileService;
import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysFileDao;
import com.seezoon.dao.modules.sys.entity.SysFile;
import com.seezoon.framework.component.file.FileInfo;

/**
 * 文件
 *
 * @author seezoon-generator 2021年1月2日 上午1:04:41
 */
@Service
public class SysFileService extends AbstractCrudService<SysFileDao, SysFile, String> {

    @Autowired
    private FileService fileService;

    public FileInfo upload(@NotEmpty String originalFilename, @NotEmpty String contentType, long size,
        @NotNull InputStream in) throws IOException {
        // 新命名
        String fileId = createFileId();
        String newName = rename(fileId, originalFilename);
        String relativePath = createRelativeDirectory() + newName;
        fileService.upload(relativePath, in);
        // 入库
        SysFile sysFile = new SysFile();
        sysFile.setId(fileId);
        sysFile.setName(originalFilename);
        sysFile.setContentType(contentType);
        sysFile.setFileSize((int)size);
        sysFile.setRelativePath(relativePath);
        this.save(sysFile);
        return new FileInfo(fileId, fileService.getUrl(relativePath), relativePath, originalFilename);
    }

    /**
     * 生成文件id
     *
     * @return
     */
    private String createFileId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成新的文件名
     *
     * @param fileId
     * @param originalFileName
     * @return
     */
    public String rename(String fileId, String originalFileName) {
        return fileId + getFilesuffix(originalFileName);
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     *            包含后缀
     * @return
     */
    public String getFilesuffix(String fileName) {
        Assert.hasText(fileName, "fileName must not be empty");
        String suffix = "";
        int lastIndex = -1;
        if (-1 != (lastIndex = fileName.lastIndexOf("."))) {
            suffix = fileName.substring(lastIndex);
        }
        return suffix;
    }

    /**
     * 生成相对目录
     *
     * @return
     */
    private String createRelativeDirectory() {
        LocalDate now = LocalDate.now();
        return "/" + now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth() + "/";
    }
}
