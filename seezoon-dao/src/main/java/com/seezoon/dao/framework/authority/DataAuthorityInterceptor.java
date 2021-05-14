package com.seezoon.dao.framework.authority;

import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.SimpleTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.PageHelper;
import com.seezoon.dao.framework.entity.PageCondition;

/**
 * @author hdf
 */
@Intercepts({
    @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,
            BoundSql.class}),
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class DataAuthorityInterceptor implements Interceptor {

    protected final Logger logger = LoggerFactory.getLogger(DataAuthorityInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        DataAuthority dataAuthority = DataAuthorityLoader.getInstance();
        String dsf = null;
        if (Objects.isNull(dataAuthority)) {
            logger.info("DataAuthority Spi not found, disable data authority");
        } else {
            dsf = dataAuthority.getDsf();
        }
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement)args[0];
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        // skip insert
        if (sqlCommandType.equals(SqlCommandType.INSERT)) {
            return invocation.proceed();
        }
        Object parameter = (Object)args[1];

        // 成本最低的方式 这里主要是和PageHelper 拦截器针对参数处理不一样，PageHelper 对map类型（WrapContextMap） 直接用了putAll，
        // 如果要兼容需要把parameter 都放到map中，对简单类型字段
        if (sqlCommandType.equals(SqlCommandType.SELECT) && null != PageHelper.getLocalPage() && null != parameter
            && parameter instanceof PageCondition) {
            ((PageCondition)parameter).setDsf(dsf);
        }

        WrapContextMap wrapContextMap = new WrapContextMap(parameter);
        // 自定义附件参数，多租户也可以自定义参数
        wrapContextMap.put("dsf", dsf);
        args[1] = wrapContextMap;
        return invocation.proceed();
    }

    @Override
    public void setProperties(Properties properties) {
        // 需要插件传参时候使用
    }

    /**
     * 在参数上下中自动加入数据权限过滤的字符串 ${dsf}
     *
     * @see <a>https://blog.csdn.net/lqzkcx3/article/details/80820327</a>
     *      参考{@link org.apache.ibatis.scripting.xmltags.DynamicContext}中{@code ContextMap} 的实现
     */
    class WrapContextMap extends HashMap<String, Object> {

        private final Object parameter;
        private final MetaObject metaObject;

        public WrapContextMap(Object parameter) {
            this.parameter = parameter;
            if (null != parameter) {
                this.metaObject = SystemMetaObject.forObject(parameter);
            } else {
                this.metaObject = null;
            }
        }

        @Override
        public Object get(Object key) {
            String strKey = (String)key;
            if (super.containsKey(strKey)) {
                return super.get(strKey);
            }

            if (null == parameter) {
                return null;
            }

            if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
                return parameter;
            }

            if (metaObject != null) {
                Object object = metaObject.getValue(strKey);
                return object;
            }

            return null;
        }
    }
    // @formatter:off
    /**          sql 改写后替换原始MappedStatement.
     *            BoundSql boundSql = ms.getBoundSql(parameter);
     *            String sql = boundSql.getSql();
     *            log.info("rewrite sql, origin sql: [{}], new sql: [{}]", sql, newSql);
     *             BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), newSql,
     *                     boundSql.getParameterMappings(), boundSql.getParameterObject());
     *             // copy原始MappedStatement的各项属性
     *             MappedStatement.Builder builder =
     *                     new MappedStatement.Builder(mappedStatement.getConfiguration(), mappedStatement.getId(),
     *                             new WarpBoundSqlSqlSource(newBoundSql), mappedStatement.getSqlCommandType());
     *             builder.cache(mappedStatement.getCache()).databaseId(mappedStatement.getDatabaseId())
     *                     .fetchSize(mappedStatement.getFetchSize())
     *                     .flushCacheRequired(mappedStatement.isFlushCacheRequired())
     *                     .keyColumn(StringUtils.join(mappedStatement.getKeyColumns(), ','))
     *                     .keyGenerator(mappedStatement.getKeyGenerator())
     *                     .keyProperty(StringUtils.join(mappedStatement.getKeyProperties(), ','))
     *                     .lang(mappedStatement.getLang()).parameterMap(mappedStatement.getParameterMap())
     *                     .resource(mappedStatement.getResource()).resultMaps(mappedStatement.getResultMaps())
     *                     .resultOrdered(mappedStatement.isResultOrdered())
     *                     .resultSets(StringUtils.join(mappedStatement.getResultSets(), ','))
     *                     .resultSetType(mappedStatement.getResultSetType()).statementType(mappedStatement.getStatementType())
     *                     .timeout(mappedStatement.getTimeout()).useCache(mappedStatement.isUseCache());
     *             MappedStatement newMappedStatement = builder.build();
     *             // 将新生成的MappedStatement对象替换到参数列表中
     *             args[0] = newMappedStatement;
     */
    // @formatter:on
}
