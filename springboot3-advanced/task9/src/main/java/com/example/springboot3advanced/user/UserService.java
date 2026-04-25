package com.example.springboot3advanced.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;

    // "users" という名前のキャッシュに保存
    // キーは引数の email になる
    @Cacheable(value = "users", key = "#email")
    public User getUserByEmail(String email) {
        log.info("Fetching user from database: {}", email);
        
        // DBアクセスをシミュレートするための遅延（本来は不要）
        try {
            Thread.sleep(3000); // 3秒待機
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @CacheEvict(value = "users", key = "#email")
    public void clearCache(String email) {
        log.info("Cache cleared for: {}", email);
    }
}
