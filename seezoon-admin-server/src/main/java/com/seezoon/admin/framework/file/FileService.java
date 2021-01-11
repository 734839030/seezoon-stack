package com.seezoon.admin.framework.file;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.seezoon.admin.framework.service.AbstractBaseService;
import com.seezoon.framework.component.file.handler.FileHandler;

import lombok.AllArgsConstructor;

/**
 * 文件模块防腐层
 *
 * @author hdf
 */
@Service
@AllArgsConstructor
public class FileService extends AbstractBaseService {

    private final FileHandler fileHandler;

    public void upload(String relativePath, InputStream in) throws IOException {
        this.fileHandler.upload(relativePath, in);
    }

    public InputStream download(String relativePath) throws IOException {
        return this.fileHandler.download(relativePath);
    }

    public void delete(String relativePath) throws IOException {
        this.fileHandler.delete(relativePath);
    }

    public String getUrl(String relativePath) {
        return this.fileHandler.getUrl(relativePath);
    }
}
