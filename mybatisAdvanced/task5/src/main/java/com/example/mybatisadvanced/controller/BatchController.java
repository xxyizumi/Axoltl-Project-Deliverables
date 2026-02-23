package com.example.mybatisadvanced.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mybatisadvanced.entity.User;
import com.example.mybatisadvanced.service.BatchService;
import com.example.mybatisadvanced.service.CursorService;

@RestController
@RequestMapping("/api/batch")
public class BatchController {
    
    private final BatchService batchService;
    private final CursorService cursorService;

    public BatchController(BatchService batchService, CursorService cursorService) {
        this.batchService = batchService;
        this.cursorService = cursorService;
    }

    
    @PostMapping("/users/batch")
    public Map<String, Object> insertUsersBatch(
            @RequestParam(defaultValue = "1000") int count,
            @RequestParam(defaultValue = "100") int batchSize) {

        List<User> users = batchService.generateTestUsers(count);

        long startTime = System.currentTimeMillis();
        int insertedCount = batchService.insertUsersBatch(users, batchSize);
        long endTime = System.currentTimeMillis();

        Map<String, Object> result = new HashMap<>();
        result.put("method", "ExecutorType.BATCH");
        result.put("count", insertedCount);
        result.put("elapsedTimeMs", endTime - startTime);

        return result;
    }

    @GetMapping("/orders/cursor")
    public Map<String, Object> getOrdersCursor() {

        long startTime = System.currentTimeMillis();
        long insertedCount = cursorService.calculateTotalAmount();
        long endTime = System.currentTimeMillis();

        Map<String, Object> result = new HashMap<>();
        result.put("method", "Cursor");
        result.put("count", insertedCount);
        result.put("elapsedTimeMs", endTime - startTime);

        return result;
    }
}
