package com.example.mybatisbasics.entity;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long userId;
    private String productName;
    private int amount;
    private LocalDateTime orderedAt;

    // デフォルトコンストラクタ
    public Order() {
    }

    // 全引数コンストラクタ
    public Order(Long id, Long userId, String productName, int amount, LocalDateTime orderedAt) {
        this.id = id;
        this.userId = userId;
        this.productName = productName;
        this.amount = amount;
        this.orderedAt = orderedAt;
    }
    
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getProductName() {
        return productName;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", userId=" + userId + ", productName=" + productName + ", amount=" + amount
                + ", orderedAt=" + orderedAt + "]";
    }
}
