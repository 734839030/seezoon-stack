package com.seezoon.dao.modules.sys;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.BaseDao;
import com.seezoon.dao.modules.sys.entity.SysUserRole;

/**
 * 用户-角色
 *
 * @author seezoon-generator 2021年1月26日 下午11:14:43
 */
@Repository
@Mapper
public interface SysUserRoleDao extends BaseDao {

    List<Integer> selectRoleIdsByUserId(@NotNull Integer userId);

    List<Integer> selectUserIdsByRoleId(@NotNull Integer roleId);

    int deleteByUser(@NotEmpty Integer... userIds);

    int deleteByRoleAndUser(@NotNull @Param("roleId") Integer roleId, @NotEmpty @Param("userIds") Integer... userIds);

    int deleteByRole(@NotNull Integer... roleIds);

    int insert(@Valid @NotEmpty SysUserRole... sysUserRoles);
}