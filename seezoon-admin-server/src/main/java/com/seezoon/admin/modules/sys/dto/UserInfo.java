package com.seezoon.admin.modules.sys.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author hdf
 */
@Getter
@Setter
@RequiredArgsConstructor
public class UserInfo implements Serializable {

    private final Integer userId;
    private final Integer deptId;
    private final String username;
    private final String name;
    private String deptName;
    private String photo;

}
