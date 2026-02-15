package com.example.mybatisbasics.mapper;

import java.util.List;

import com.example.mybatisbasics.entity.Order;

public interface OrderMapper {
     /**
     * 全注文を取得する
     */
    List<Order> findAll();

    /**
     * IDで注文を検索する
     */
    Order findById(Long id);

    /**
     * 注文を新規登録する
     * @param Order 登録する注文
     * @return 影響を受けた行数
     */
    int insert(Order Order);

    /**
     * 注文情報を更新する
     * @param Order 更新する注文
     * @return 影響を受けた行数
     */
    int update(Order Order);

    /**
     * 注文を削除する
     * @param id 削除する注文のID
     * @return 影響を受けた行数
     */
    int deleteById(Long id);

    /**
     * 注文の一括削除
     * @param ids 削除する注文のIDのリスト
     * @return 影響を受けた行数
     */
    int deleteByIds(List<Long> ids);
}
