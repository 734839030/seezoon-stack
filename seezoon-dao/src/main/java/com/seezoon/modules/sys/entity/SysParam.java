package com.seezoon.modules.sys.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.seezoon.framework.entity.BaseEntity;

import lombok.Data;

/**
 * @author hdf
 */
@Data
public class SysParam extends BaseEntity<Long> {

    @NotEmpty
    @Length(min = 1, max = 50)
    private String name;

    @NotEmpty
    @Length(min = 1, max = 50)
    private String paramKey;

    @NotEmpty
    @Length(min = 1, max = 100)
    private String paramValue;

}