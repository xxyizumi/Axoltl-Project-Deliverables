package com.example.mybatisbasics.mapper;

import com.example.mybatisbasics.entity.User;
import java.util.List;

public interface UserMapper {
    
    /**
     * 全ユーザーを取得する
     */
    List<User> findAll();

    /**
     * IDでユーザーを検索する
     */
    User findById(Long id);

    /**
     * メールアドレスでユーザーを検索する
     */
    User findByEmail(String email);
    
    /**
     * ユーザーの総数を取得する
     */
    int count();

    /**
     * ユーザ名の部分一致で検索する
     */
    User findByNameLike(String name);
}
