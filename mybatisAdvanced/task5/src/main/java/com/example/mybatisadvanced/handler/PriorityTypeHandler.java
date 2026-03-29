package com.example.mybatisadvanced.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.example.mybatisadvanced.entity.Priority;

@MappedTypes(BaseTypeHandler.class)
public class PriorityTypeHandler extends BaseTypeHandler<Priority> {

    /**
     * Javaオブジェクト → データベース
     * PreparedStatementにパラメータをセットする際に呼ばれる
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, 
            Priority parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    /**
     * データベース → Javaオブジェクト（カラム名で取得）
     */
    @Override
    public Priority getNullableResult(ResultSet rs, String columnName) 
            throws SQLException {
        String value = rs.getString(columnName);
        return convertToPriority(value);
    }

    /**
     * データベース → Javaオブジェクト（カラムインデックスで取得）
     */
    @Override
    public Priority getNullableResult(ResultSet rs, int columnIndex) 
            throws SQLException {
        String value = rs.getString(columnIndex);
        return convertToPriority(value);
    }

    /**
     * データベース → Javaオブジェクト（CallableStatement用）
     */
    @Override
    public Priority getNullableResult(CallableStatement cs, int columnIndex) 
            throws SQLException {
        String value = cs.getString(columnIndex);
        return convertToPriority(value);
    }

    /**
     * 文字列をOrderStatusに変換（共通処理）
     */
    private Priority convertToPriority(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Priority.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unknown OrderStatus: " + value, e);
        }
    }
}
