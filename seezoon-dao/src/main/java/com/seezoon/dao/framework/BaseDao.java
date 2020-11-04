package com.seezoon.dao.framework;

import org.springframework.validation.annotation.Validated;

/**
 * <pre>
 * Validated 的目的，DAO 完成基础参数验证,实体中的字段验证本可以再DAO一起统一做了，
 * 考虑到上游web 层为了方便，复用实体接收参数会有一层校验
 * </pre>
 *
 * @author hdf
 */
@Validated
public interface BaseDao {

}
