package com.example.mybatisadvanced.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mybatisadvanced.entity.Order;
import com.example.mybatisadvanced.entity.OrderStatus;
import com.example.mybatisadvanced.mapper.OrderMapper;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    
    private final OrderMapper orderMapper;
    
    public StatusController(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /**
     * 注文のステータスを更新
     */
    @PutMapping("/orders/{id}")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        
        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            Order before = orderMapper.findById(id);
            
            if (before == null) {
                return ResponseEntity.notFound().build();
            }
            
            // ステータスを更新（TypeHandlerがEnumを変換）
            orderMapper.updateStatus(id, newStatus);
            
            Order after = orderMapper.findById(id);
            
            Map<String, Object> result = new HashMap<>();
            result.put("orderId", id);
            result.put("before", before.getStatus().name());
            result.put("after", after.getStatus().name());
            
            return ResponseEntity.ok(result);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "無効なステータス: " + status)
            );
        }
    }
    
    /**
     * 指定ステータスの注文一覧を取得
     */
    @GetMapping("/orders/by-status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            List<Order> orders = orderMapper.findByStatus(orderStatus);
            
            return ResponseEntity.ok(Map.of(
                "status", orderStatus.name(),
                "count", orders.size(),
                "orders", orders
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "無効なステータス: " + status)
            );
        }
    }
}
