package com.example.mybatisadvanced.plugin;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

/**
 * 監査用インターセプター
 * 
 * INSERTとUPDATE時にcreated_atとupdated_atを自動設定する。
 */
@Intercepts({
    @Signature(
        type = Executor.class, 
        method = "update", 
        args = {MappedStatement.class, Object.class}
    )
})
public class AuditInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        if (parameter != null) {
            SqlCommandType sqlCommandType = ms.getSqlCommandType();
            LocalDateTime now = LocalDateTime.now();

            // INSERTの場合：createdAtとupdatedAtを設定
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                setFieldValue(parameter, "createdAt", now);
                setFieldValue(parameter, "updatedAt", now);
            }
            // UPDATEの場合：updatedAtのみ設定
            else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                setFieldValue(parameter, "updatedAt", now);
            }
        }

        // 元のメソッドを実行
        return invocation.proceed();
    }

    /**
     * リフレクションでフィールドに値を設定
     */
    private void setFieldValue(Object target, String fieldName, Object value) {
        try {
            Field field = findField(target.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                // フィールドがnullの場合のみ設定
                if (field.get(target) == null) {
                    field.set(target, value);
                }
            }
        } catch (Exception e) {
            // フィールドが存在しない場合は無視
        }
    }

    private Field findField(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        // 設定プロパティがあれば処理
    }
}