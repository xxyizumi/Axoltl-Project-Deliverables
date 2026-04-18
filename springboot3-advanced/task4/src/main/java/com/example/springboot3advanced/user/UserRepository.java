package com.example.springboot3advanced.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // emailでユーザーを検索（認証時に使用）
    Optional<User> findByEmail(String email);
}
