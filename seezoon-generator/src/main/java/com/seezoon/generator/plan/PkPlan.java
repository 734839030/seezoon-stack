package com.seezoon.generator.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@Getter
@Setter
@AllArgsConstructor
public class PkPlan {
    private String dbColumnName;
    private String jdbcType;
    private String javaType;

}
