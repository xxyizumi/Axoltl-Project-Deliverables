package com.example.mybatisadvanced.entity;

public enum OrderStatus {
    PENDING,     // 注文受付待ち
    CONFIRMED,   // 注文確定
    SHIPPED,     // 発送済み
    DELIVERED,   // 配達完了
    CANCELLED    // キャンセル
}
