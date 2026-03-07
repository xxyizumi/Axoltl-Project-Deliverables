package com.example.thymeleaf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * TODOリポジトリ
 * Spring Data JPAによりCRUD操作が自動実装される
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    // 完了/未完了でフィルタリング
    List<Todo> findByCompleted(boolean completed);
    
    // 作成日時の降順で全件取得
    List<Todo> findAllByOrderByCreatedAtDesc();
    
    // 未完了のTODOを作成日時の昇順で取得
    List<Todo> findByCompletedFalseOrderByCreatedAtAsc();
}