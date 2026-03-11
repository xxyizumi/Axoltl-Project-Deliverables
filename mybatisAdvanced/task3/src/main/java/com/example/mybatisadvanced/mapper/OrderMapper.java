package com.example.mybatisadvanced.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;

import com.example.mybatisadvanced.entity.Order;

@Mapper
public interface OrderMapper {

    Order findByUserId(Long userId);
    Order findByIdWithUser(Long id);
    Order findByIdWithDetails(Long id);

    /**
     * カーソルで全注文を取得
     */
    Cursor<Order> findAllWithCursor();
}
