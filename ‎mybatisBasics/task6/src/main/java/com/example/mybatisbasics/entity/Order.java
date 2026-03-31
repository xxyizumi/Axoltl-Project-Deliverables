package com.example.mybatisbasics.entity;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long userId;
    private String productName;
    private Integer amount;
    private LocalDateTime orderedAt;

    public Order() {
    }

    // Getter
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getProductName() { return productName; }
    public Integer getAmount() { return amount; }
    public LocalDateTime getOrderedAt() { return orderedAt; }

    // Setter
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public void setOrderedAt(LocalDateTime orderedAt) { this.orderedAt = orderedAt; }
}
