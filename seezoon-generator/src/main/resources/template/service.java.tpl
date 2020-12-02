package com.seezoon.admin.modules.${moduleName}.service;

import org.springframework.stereotype.Service;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.${moduleName}.${className}Dao;
import com.seezoon.dao.modules.${moduleName}.entity.${className};

/**
 * ${menuName!}
 * @author seezoon-generator ${.now}
 */
@Service
public class ${className}Service extends AbstractCrudService<${className}Dao, ${className}, ${pkPlan.dataType.javaType()}> {

}
