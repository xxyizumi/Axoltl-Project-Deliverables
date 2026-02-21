package com.example.mybatisadvanced.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.mybatisadvanced.entity.User;
import com.example.mybatisadvanced.mapper.UserMapper;



@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserMapper userMapper;
    
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 全ユーザーを取得（注文なし）
     */
    @GetMapping
    public List<User> findAll() {
        return userMapper.findAll();
    }
    
    /**
     * IDでユーザーを取得（注文履歴付き）
     * 1対多のリレーションマッピングを使用
     */
    @GetMapping("/{id}/with-orders")
    public ResponseEntity<User> findByIdWithOrders(@PathVariable Long id) {
        User user = userMapper.findByIdWithOrders(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    
    /**
     * 全ユーザーを注文履歴付きで取得（N+1問題解決版）
     */
    @GetMapping("/with-orders")
    public List<User> findAllWithOrders() {
        return userMapper.findAllWithOrders();
    }

    @GetMapping("/with-orders-and-orderDetails")
    public List<User> findAllWithOrdersAndOrderDetails() {
        return userMapper.findAllWithOrdersAndOrderDetails();
    }

    @GetMapping("/with-orders-for-lazy")
    public List<User> findAllForLazyWithOrders() {
        return userMapper.findAllForLazyWithOrders();
    }
}