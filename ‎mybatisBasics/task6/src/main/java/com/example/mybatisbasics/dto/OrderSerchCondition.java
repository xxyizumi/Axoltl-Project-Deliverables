package com.example.mybatisbasics.dto;

import java.util.List;

import com.example.mybatisbasics.entity.Order;

public class OrderSerchCondition {
    private Long userId;
    private String name;
    private String email;
    private List<Order> orders;

    public OrderSerchCondition() {
    }   

    public OrderSerchCondition(Long userId, String name, String email, List<Order> orders) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.orders = orders;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }    
}