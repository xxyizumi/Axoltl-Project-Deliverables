package com.example.mybatisadvanced.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;

import com.example.mybatisadvanced.entity.Order;
import com.example.mybatisadvanced.entity.OrderStatus;

@Mapper
public interface OrderMapper {

    Order findById(Long id);
    Order findByUserId(Long userId);
    Order findByIdWithUser(Long id);
    Order findByIdWithDetails(Long id);

    /**
     * カーソルで全注文を取得
     */
    Cursor<Order> findAllWithCursor();
    void updateStatus(Long id, OrderStatus status);
    List<Order> findByStatus(OrderStatus status);
}
