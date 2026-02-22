package com.example.mybatisadvanced.entity;

import java.time.LocalDateTime;
import java.util.List;

public class User {
    
    private Long id;
    private String name;
    private String email;
    private List<String> jsons;
    private List<Order> orders;  // 1対多のリレーション
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // コンストラクタ、Getter/Setter
    public User() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<String> getJsons() {
        return jsons;
    }
    public void setJsons(List<String> jsons) {
        this.jsons = jsons;
    }
}