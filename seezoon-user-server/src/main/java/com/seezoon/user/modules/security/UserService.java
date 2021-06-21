package com.seezoon.user.modules.security;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.user.framework.file.FileService;
import com.seezoon.user.framework.service.AbstractTransactionService;
import com.seezoon.user.modules.security.dto.UserInfo;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@RequiredArgsConstructor
@Service
public class UserService extends AbstractTransactionService {

    private final FileService fileService;

    @Transactional(readOnly = true)
    public UserInfo userInfo(@NotNull Integer userId) {
        return null;
    }
}
