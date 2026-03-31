package com.example.mybatisbasics.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mybatisbasics.entity.User;
import com.example.mybatisbasics.mapper.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper;

    // コンストラクタインジェクション
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Transactional
    public User create(User user) {
        userMapper.insert(user);
        return user;  // idが自動セットされている
    }

    @Transactional
    public User update(Long id, User user) {
        User existing = userMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("User not found: " + id);
        }
        user.setId(id);
        userMapper.update(user);
        return userMapper.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        User existing = userMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("User not found: " + id);
        }
        userMapper.deleteById(id);
    }

    public List<User> search(String name, String email) {
        return userMapper.findByCondition(name, email);
    }
}
