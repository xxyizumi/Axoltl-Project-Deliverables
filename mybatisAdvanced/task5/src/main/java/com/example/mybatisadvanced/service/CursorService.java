package com.example.mybatisadvanced.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mybatisadvanced.entity.Order;
import com.example.mybatisadvanced.entity.User;
import com.example.mybatisadvanced.mapper.OrderMapper;
import com.example.mybatisadvanced.mapper.UserMapper;

@Service
public class CursorService {
    
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    public CursorService(UserMapper userMapper, OrderMapper orderMapper) {
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
    }
    
    /**
     * カーソルで全ユーザーを処理
     */
    @Transactional(readOnly = true)  // トランザクション内で実行が必須
    public int processAllUsersWithCursor() {
        AtomicInteger count = new AtomicInteger(0);
        
        try (Cursor<User> cursor = userMapper.findAllWithCursor()) {
            cursor.forEach(user -> {
                // 各ユーザーに対する処理
                processUser(user);
                count.incrementAndGet();
            });
        } catch (Exception e) {
            throw new RuntimeException("Cursor processing failed", e);
        }
        
        return count.get();
    }

    @Transactional(readOnly = true)
    public long calculateTotalAmount() {
        long total = 0;
        try (Cursor<Order> cursor = orderMapper.findAllWithCursor()) {
            for (Order order : cursor) {
                total += order.getTotalAmount();
            }
        } catch (Exception e) {
            throw new RuntimeException("Cursor processing failed", e);
        }
        return total;
    }
    private void processUser(User user) {
        // 実際の処理（ログ出力、変換など）
        System.out.println("Processing user: " + user.getId());
    }
}
