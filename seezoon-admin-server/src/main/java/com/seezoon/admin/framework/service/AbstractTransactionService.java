package com.seezoon.admin.framework.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * 带事务的service
 *
 * 事务粒度过大，但是方便，对于高性能要求的项目不要在父类加
 *
 * @author hdf
 */
@Transactional(rollbackFor = Exception.class)
public abstract class AbstractTransactionService extends AbstractBaseService {

}
