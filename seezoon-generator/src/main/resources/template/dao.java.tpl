package com.seezoon.dao.modules.${moduleName};

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.${moduleName}.entity.${className};

/**
 * ${menuName!}
 * @author seezoon-generator ${.now}
 */
@Repository
@Mapper
public interface ${className}Dao extends CrudDao<${className}, ${pkPlan.dataType.javaType()}> {

}