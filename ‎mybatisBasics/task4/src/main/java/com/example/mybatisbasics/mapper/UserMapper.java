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
     * ユーザーを新規登録する
     * @param user 登録するユーザー
     * @return 影響を受けた行数
     */
    int insert(User user);

    /**
     * ユーザー情報を更新する
     * @param user 更新するユーザー
     * @return 影響を受けた行数
     */
    int update(User user);

    /**
     * ユーザーを削除する
     * @param id 削除するユーザーのID
     * @return 影響を受けた行数
     */
    int deleteById(Long id);

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
