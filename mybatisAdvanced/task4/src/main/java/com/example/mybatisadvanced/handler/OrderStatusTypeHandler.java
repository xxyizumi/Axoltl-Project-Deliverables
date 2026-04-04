package com.example.mybatisadvanced.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.example.mybatisadvanced.entity.OrderStatus;

/**
 * OrderStatus用TypeHandler
 * 
 * OrderStatus Enumとデータベースの文字列（VARCHAR）間の変換を行う。
 */
@MappedTypes(OrderStatus.class)
public class OrderStatusTypeHandler extends BaseTypeHandler<OrderStatus> {

    /**
     * Javaオブジェクト → データベース
     * PreparedStatementにパラメータをセットする際に呼ばれる
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, 
            OrderStatus parameter, JdbcType jdbcType) throws SQLException {
        // Enumのname()を使用して文字列として保存
        ps.setString(i, parameter.name());
    }

    /**
     * データベース → Javaオブジェクト（カラム名で取得）
     */
    @Override
    public OrderStatus getNullableResult(ResultSet rs, String columnName) 
            throws SQLException {
        String value = rs.getString(columnName);
        return convertToOrderStatus(value);
    }

    /**
     * データベース → Javaオブジェクト（カラムインデックスで取得）
     */
    @Override
    public OrderStatus getNullableResult(ResultSet rs, int columnIndex) 
            throws SQLException {
        String value = rs.getString(columnIndex);
        return convertToOrderStatus(value);
    }

    /**
     * データベース → Javaオブジェクト（CallableStatement用）
     */
    @Override
    public OrderStatus getNullableResult(CallableStatement cs, int columnIndex) 
            throws SQLException {
        String value = cs.getString(columnIndex);
        return convertToOrderStatus(value);
    }

    /**
     * 文字列をOrderStatusに変換（共通処理）
     */
    private OrderStatus convertToOrderStatus(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return OrderStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unknown OrderStatus: " + value, e);
        }
    }
}
