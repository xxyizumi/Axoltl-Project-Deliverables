package com.example.mybatisadvanced.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.example.mybatisadvanced.entity.User;
import com.example.mybatisadvanced.mapper.UserMapper;

@Service
public class BatchService {
    
    private final SqlSessionFactory sqlSessionFactory;
    
    private final UserMapper userMapper;

    public BatchService(SqlSessionFactory sqlSessionFactory, UserMapper userMapper) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.userMapper = userMapper;
    }
    
    public List<User> generateTestUsers(int count) {
        return IntStream.range(0, count).mapToObj(i->{
            String userId = String.valueOf(i + 1);
            User user = new User();
            user.setId(Long.parseLong(userId));
            user.setName("サンプルユーザ" + userId);
            user.setEmail("test.user" + userId+ "@example.com");
            user.setOrders(new ArrayList<>());
            user.setUpdatedAt(LocalDateTime.now());
            return user;
        }).collect(Collectors.toList());
    }

    /**
     * バッチ処理でユーザーを一括登録
     */
    public int insertUsersBatch(List<User> users, int batchSize) {
        // ExecutorType.BATCHでSqlSessionを開く
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            UserMapper batchMapper = sqlSession.getMapper(UserMapper.class);
            
            int totalCount = 0;
            for (int i = 0; i < users.size(); i++) {
                batchMapper.insert(users.get(i));
                totalCount++;
                
                // batchSize件ごとにflush（データベースに送信）
                if ((i + 1) % batchSize == 0) {
                    sqlSession.flushStatements();
                }
            }
            
            // 残りをflush
            sqlSession.flushStatements();
            
            // コミット
            sqlSession.commit();
            
            return totalCount;
        }
    }

    public int insertUsersBulk(List<User> users, int batchSize) {
        int totalCount = 0;
        
        // batchSizeごとに分割してINSERT
        for (int i = 0; i < users.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, users.size());
            List<User> batch = users.subList(i, endIndex);
            userMapper.insertBatch(batch);
            totalCount += batch.size();
        }
        
        return totalCount;
    }
}
