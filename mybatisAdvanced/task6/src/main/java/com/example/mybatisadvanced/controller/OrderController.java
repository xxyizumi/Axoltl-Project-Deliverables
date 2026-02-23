package com.example.mybatisadvanced.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.mybatisadvanced.entity.Order;
import com.example.mybatisadvanced.mapper.OrderMapper;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderMapper orderMapper;
    
    public OrderController(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }
    
    /**
     * IDで注文を取得（ユーザー情報付き）
     * 多対1のリレーションマッピング（association）を使用
     */
    @GetMapping("/{id}/with-user")
    public ResponseEntity<Order> findByIdWithUser(@PathVariable Long id) {
        Order order = orderMapper.findByIdWithUser(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }
    
    /**
     * IDで注文を取得（注文明細付き）
     * 1対多のリレーションマッピング（collection）を使用
     */
    @GetMapping("/{id}/with-details")
    public ResponseEntity<Order> findByIdWithDetails(@PathVariable Long id) {
        Order order = orderMapper.findByIdWithDetails(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }
}