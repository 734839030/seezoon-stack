package com.seezoon.admin.modules.sys.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.framework.dto.Tree;
import com.seezoon.dao.modules.sys.SysMenuDao;
import com.seezoon.dao.modules.sys.entity.SysMenu;
import com.seezoon.dao.modules.sys.entity.SysMenuCondition;
import com.seezoon.framework.utils.IdGen;
import com.seezoon.framework.utils.TreeHelper;

/**
 * 菜单管理
 *
 * @author seezoon-generator 2021年1月31日 上午12:21:33
 */
@Service
public class SysMenuService extends AbstractCrudService<SysMenuDao, SysMenu, Integer> {

    /**
     * 返回全部菜单的
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<SysMenu> findTreeTable() {
        List<SysMenu> sysMenus = this.find(new SysMenuCondition());
        Map<Integer, List<SysMenu>> parentIdGroup =
            sysMenus.stream().collect(Collectors.groupingBy(sysMenu -> sysMenu.getParentId()));
        // 先找到根节点
        List<SysMenu> root = parentIdGroup.get(TreeHelper.DEFAULT_PARENT_ID);
        this.setChildren(root, parentIdGroup);
        return root == null ? Collections.emptyList() : root;
    }

    /**
     * 查询子节点
     *
     * @param parentId
     * @return
     */
    @Transactional(readOnly = true)
    public List<SysMenu> findByParentId(@NotNull Integer parentId) {
        SysMenuCondition sysMenuCondition = new SysMenuCondition();
        sysMenuCondition.setParentId(parentId);
        return this.find(sysMenuCondition);
    }

    /**
     * 查询树结构
     *
     * @param parentId
     * @param includeChild
     *            是否包含子节点的子节点
     * @return
     */
    @Transactional(readOnly = true)
    public List<Tree> findTree(@NotNull Integer parentId, boolean includeChild) {
        List<Tree> trees = new ArrayList<>();
        List<SysMenu> menus = this.findByParentId(parentId);
        menus.forEach((menu) -> {
            Tree tree = Tree.builder().key(IdGen.uuid()).value(menu.getId()).title(menu.getName())
                .children(includeChild ? this.findTree(menu.getId(), includeChild) : null).selectable(true).build();
            trees.add(tree);
        });
        return trees;
    }

    /**
     * 递归设置子节点
     *
     * @param sysMenus
     * @param parentIdGroup
     */
    private void setChildren(List<SysMenu> sysMenus, Map<Integer, List<SysMenu>> parentIdGroup) {
        if (null == sysMenus || sysMenus.isEmpty()) {
            return;
        }
        sysMenus.forEach((sysMenu -> {
            List<SysMenu> children = parentIdGroup.get(sysMenu.getId());
            this.setChildren(children, parentIdGroup);
            sysMenu.setChildren(children);
        }));
    }

    public int delete(@NotNull @Min(1) Integer id) {
        List<Integer> childrenIds = this.findAllChildren(id).stream().map(SysMenu::getId).collect(Collectors.toList());
        childrenIds.add(id);
        int cnt = super.delete(childrenIds.toArray(Integer[]::new));
        logger.info("delete menu size:[{}]", cnt);
        return cnt;
    }

    /**
     * 找到自己的所有孩子
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<SysMenu> findAllChildren(@NotNull Integer id) {
        SysMenuCondition sysMenuCondition = new SysMenuCondition();
        sysMenuCondition.setParentIds(TreeHelper.getQueryParentIds(id));
        return this.find(sysMenuCondition);
    }

    public int save(@NotNull SysMenu record) {
        this.resolveParentIds(record);
        return super.save(record);
    }

    @Override
    public int updateSelective(@NotNull SysMenu record) {
        // 检查是否修改过父部门
        SysMenu current = this.find(record.getId());
        if (Objects.equals(current.getParentId(), record.getParentId())) {
            return super.updateSelective(record);
        } else {
            String oldParentIds = current.getParentIds();
            this.resolveParentIds(record);

            this.findAllChildren(record.getId()).forEach((child) -> {
                child.setParentIds(child.getParentIds().replace(oldParentIds, oldParentIds));
                super.updateSelective(child);
            });
            return super.updateSelective(record);
        }
    }

    /**
     * 生成新的parentIds
     *
     * @param record
     */
    private void resolveParentIds(SysMenu record) {
        // 处理parentIds
        Integer parentId = record.getParentId();
        Assert.notNull(parentId, "parentId must be not null");
        if (parentId == TreeHelper.DEFAULT_PARENT_ID) {
            record.setParentIds(TreeHelper.DEFAULT_PARENT_IDS);
        } else {
            SysMenu parent = this.find(parentId);
            Assert.notNull(parent, "父节点不存在");
            record.setParentIds(TreeHelper.getCurrentParentIds(record.getId(), parentId, parent.getParentIds()));
        }
    }

}
