package com.seezoon.admin.framework.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * 带事务的service
 *
 * @author hdf
 */
@Transactional(rollbackFor = Exception.class)
public abstract class AbstractTransactionService extends AbstractBaseService {

}
