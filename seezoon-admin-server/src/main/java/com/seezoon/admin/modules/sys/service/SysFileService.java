package com.seezoon.admin.modules.sys.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.seezoon.admin.framework.file.FileService;
import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysFileDao;
import com.seezoon.dao.modules.sys.entity.SysFile;
import com.seezoon.dao.modules.sys.entity.SysFileCondition;
import com.seezoon.framework.component.file.FileInfo;

import lombok.RequiredArgsConstructor;

/**
 * 文件
 *
 * @author seezoon-generator 2021年1月2日 上午1:04:41
 */
@Service
@RequiredArgsConstructor
public class SysFileService extends AbstractCrudService<SysFileDao, SysFile, String> {

    public static final String COMMA = ",";
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<SysFile> findByIds(@NotEmpty List<String> ids) {
        SysFileCondition sysFileCondition = new SysFileCondition();
        sysFileCondition.setIds(ids);
        return this.find(sysFileCondition);
    }

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
        return new FileInfo(fileService.getUrl(relativePath), relativePath, originalFilename);
    }

    /**
     * 查询文件详细信息，会自动排序
     *
     * @param relativePaths
     * @param includeFileName
     * @return
     */
    @Transactional(readOnly = true)
    public List<FileInfo> info(@NotBlank String relativePaths, boolean includeFileName) {
        String[] relativePathArray = StringUtils.split(relativePaths, COMMA);
        List<String> ids = new ArrayList<>();
        Map<String, String> idMapRelativePath = new HashMap<>();
        List<FileInfo> fileInfos = new ArrayList<>();
        for (String relativePath : relativePathArray) {
            String id = fileService.getId(relativePath);
            ids.add(id);
            idMapRelativePath.put(relativePath, id);
            if (!includeFileName) {
                fileInfos.add(new FileInfo(fileService.getUrl(relativePath), relativePath, null));
            }
        }

        if (includeFileName) {
            Map<String, SysFile> relativePathMapFile = this.findByIds(ids).stream()
                .collect(Collectors.toMap(SysFile::getRelativePath, Function.identity(), (k1, k2) -> k1));
            for (String relativePath : relativePathArray) {
                SysFile sysFile = relativePathMapFile.get(relativePath);
                if (null == sysFile) {
                    logger.error("file not exists relativePath=[{}]",relativePath);
                    continue;
                }
                fileInfos.add(new FileInfo(fileService.getUrl(sysFile.getRelativePath()), sysFile.getRelativePath(),
                    sysFile.getName()));
            }
        }
        return fileInfos;
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
        return fileId + getFileSuffix(originalFileName);
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     *            包含后缀
     * @return
     */
    public String getFileSuffix(String fileName) {
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
