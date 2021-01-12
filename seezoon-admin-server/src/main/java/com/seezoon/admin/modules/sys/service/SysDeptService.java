package com.seezoon.admin.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysDeptDao;
import com.seezoon.dao.modules.sys.entity.SysDept;
import com.seezoon.dao.modules.sys.entity.SysDeptCondition;

/**
 * 组织机构
 *
 * @author seezoon-generator 2021年1月12日 下午10:54:44
 */
@Service
public class SysDeptService extends AbstractCrudService<SysDeptDao, SysDept, Integer> {

    @Transactional(readOnly = true)
    public SysDept findByName(@NotEmpty String name) {
        SysDeptCondition sysDeptCondition = new SysDeptCondition();
        sysDeptCondition.setName(name);
        return this.findOne(sysDeptCondition);
    }

    /**
     * 查询子节点
     *
     * @param parentId
     *            为null查询 父节点为空的部门
     * @return
     */
    @Transactional(readOnly = true)
    public List<SysDept> findByParentId(Integer parentId) {
        SysDeptCondition sysDeptCondition = new SysDeptCondition();
        sysDeptCondition.setParentId(parentId);
        return this.find(sysDeptCondition);
    }

    @Transactional(readOnly = true)
    public List<Integer> findParentAndChildrenIds(@NotNull @Min(1) Integer parentId) {
        List<Integer> ids = new ArrayList<>();
        SysDept parent = this.find(parentId);
        if (null != parent) {
            ids.addAll(this.findChildrenIds(parentId));
            ids.add(parent.getId());
        }
        return ids;
    }

    @Transactional(readOnly = true)
    public List<Integer> findChildrenIds(@NotNull @Min(1) Integer parentId) {
        List<Integer> ids = new ArrayList<>();
        List<SysDept> children = this.findByParentId(parentId);
        if (!children.isEmpty()) {
            children.forEach((child) -> {
                ids.add(child.getId());
                ids.addAll(this.findChildrenIds(child.getId()));
            });
        }
        return ids;
    }
}
