package com.seezoon.admin.modules.sys.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hdf
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {

    private Integer userId;
    private Integer deptId;
    private String username;
    private String name;

}
