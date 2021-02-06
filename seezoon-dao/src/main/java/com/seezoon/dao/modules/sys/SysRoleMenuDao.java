package com.seezoon.dao.modules.sys;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.BaseDao;
import com.seezoon.dao.modules.sys.entity.SysRoleMenu;

/**
 * 角色-菜单
 *
 * @author seezoon-generator 2021年2月7日 上午1:11:14
 */
@Repository
@Mapper
public interface SysRoleMenuDao extends BaseDao {

    List<Integer> selectMenuIdsByRoleId(@NotNull Integer roleId);

    List<Integer> selectRoleIdsByMenuId(@NotNull Integer menuId);

    int deleteByRole(@NotEmpty Integer... roleIds);

    int deleteByMenu(@NotNull Integer... menuIds);

    int insert(@Valid @NotEmpty SysRoleMenu... sysRoleMenus);
}