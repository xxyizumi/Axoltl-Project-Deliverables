package com.example.mybatisadvanced.entity;

import java.time.LocalDateTime;

public class OrderDetail {
    private Long id;
    private Long orderId;
    private String productName;
    private int quantity;
    private int unitPrice;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String name) {
        this.productName = name;
    }
    public int getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(int price) {
        this.unitPrice = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int stock) {
        this.quantity = stock;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
