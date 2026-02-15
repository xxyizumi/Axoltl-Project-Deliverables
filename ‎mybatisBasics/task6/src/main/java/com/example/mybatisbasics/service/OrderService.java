package com.example.mybatisbasics.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mybatisbasics.dto.OrderSerchCondition;
import com.example.mybatisbasics.entity.Order;
import com.example.mybatisbasics.mapper.OrderMapper;

@Service
public class OrderService {
    
    private final OrderMapper orderMapper;
    
    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public List<Order> findAll() {
        return orderMapper.findAll();
    }

    public Order findById(Long id) {
        return orderMapper.findById(id);
    }

    @Transactional
    public Order create(Order Order) {
        orderMapper.insert(Order);
        return Order;  // idが自動セットされている
    }

    @Transactional
    public Order update(Long id, Order Order) {
        Order existing = orderMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("Order not found: " + id);
        }
        Order.setId(id);
        orderMapper.update(Order);
        return orderMapper.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        Order existing = orderMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("Order not found: " + id);
        }
        orderMapper.deleteById(id);
    }

    public List<OrderSerchCondition> getOrdersByUserId(Long userId) {
        return orderMapper.getOrdersByUserId(userId);
    }
}
