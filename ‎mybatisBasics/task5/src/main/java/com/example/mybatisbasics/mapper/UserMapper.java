package com.example.mybatisbasics.mapper;

import com.example.mybatisbasics.dto.UserSearchCondition;
import com.example.mybatisbasics.entity.User;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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

    /**
     * 条件で検索（名前またはメール）
     */
    List<User> findByCondition(@Param("name") String name, @Param("email") String email);

    /**
     * 複数IDでユーザーを検索
     */
    List<User> findByIds(List<Long> ids);

    /**
     * 複数IDで削除
     */
    int deleteByIds(List<Long> ids);

    /**
     * 複数ユーザーを一括登録
     */
    int insertBatch(List<User> users);

    /**
     * ソート条件で検索
     * @param sortBy ソート項目（name, email, createdAt）
     */
    List<User> findAllWithSort(String sortBy);

    /**
     * 部分更新（nullでないフィールドのみ更新）
     */
    int updateSelective(User user);

    /**
     * 検索条件DTOで検索
     */
    List<User> search(UserSearchCondition condition);

    /**
     * ページネーション
     * @param page 現在のページ
     * @param size 1ページで表示する件数
     * @return ページングされた結果
     */
    List<User> findWithPagination(@Param("page") int page, @Param("size") int size);

    /**
     * 名前かemailが一致する
     */
    List<User> findNameOrEmail(@Param("name") String name, @Param("email") String email);

    /**
     * 登録日時が範囲内のユーザを検索
     */
    List<User> findWithinCreateAtRange(@Param("fromDateTime") LocalDateTime fromDateTime, @Param("toDateTime") LocalDateTime toDateTime);
}
