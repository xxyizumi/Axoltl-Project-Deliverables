package com.example.mybatisadvanced.plugin;

import java.util.List;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@Intercepts({
    @Signature(type = Executor.class, method = "update", 
               args = {MappedStatement.class, Object.class}),
    @Signature(type = Executor.class, method = "query", 
               args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class SqlLogPlugin implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(SqlLogPlugin.class);
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        // SQL情報を取得
        BoundSql boundSql = ms.getBoundSql(parameter);
        String sql = formatSql(boundSql, ms.getConfiguration());
        String sqlId = ms.getId();
        
        // 実行時間を計測
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = invocation.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            // ログ出力
            logger.info("[SQL] {} - {} ms", getSimpleSqlId(sqlId), executionTime);
            if (logger.isDebugEnabled()) {
                logger.debug("SQL: {} ", sql);
            }
            // スロークエリの検出（100ms以上で警告）
            if (executionTime >= 100) {
                logger.warn("クエリの実行に時間がかかっています。[SQL]{}", getSimpleSqlId(sqlId));
            }
            if (result instanceof Integer) {
                int affectedRows = (Integer) result;
                logger.debug("実行件数: " + affectedRows);
            } else if (result instanceof List) {
                int resultSize = ((List<?>) result).size();
                logger.debug("実行件数: " + resultSize);
            }
            return result;
        } catch (Exception e) {
            logger.error("SQL実行エラー: {}", sqlId);
            logger.error("SQL: {}", sql);
            throw e;
        }
    }
    
    /**
     * SQLを整形し、パラメータを展開
     */
    private String formatSql(BoundSql boundSql, Configuration config) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ").trim();

        // パラメータ展開ロジック...
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if(CollectionUtils.isEmpty(parameterMappings)) {
            return sql;
        }
        String createSqlStr = createSqlStr(
            parameterMappings,
            boundSql,
            sql
        );

        return createSqlStr;
    }
    
    private String getSimpleSqlId(String sqlId) {
        int lastDot = sqlId.lastIndexOf('.');
        return lastDot > 0 ? sqlId.substring(lastDot + 1) : sqlId;
    }

    private String createSqlStr(List<ParameterMapping> parameterMappings, BoundSql boundSql, String sql) {
        for (ParameterMapping parameterMapping : parameterMappings) {
            String propertyName = parameterMapping.getProperty();
            Object sqlValue = null;
            if (boundSql.hasAdditionalParameter(propertyName)) {
                sqlValue = boundSql.getAdditionalParameter(propertyName);
            }
            if("password".equalsIgnoreCase(propertyName)) {
                sqlValue = "*********";
            }
            String formatSqlValue = formatSqlValue(sqlValue);
            return sql.replace("\\?", formatSqlValue);
        }
        return sql;
    }
    private String formatSqlValue(Object value) {
        if (value == null) return "NULL";
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return value.toString();
    }
}
