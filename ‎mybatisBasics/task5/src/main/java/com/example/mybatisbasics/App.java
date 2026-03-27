package com.example.mybatisbasics;

import com.example.mybatisbasics.entity.Order;
import com.example.mybatisbasics.entity.User;
import com.example.mybatisbasics.mapper.OrderMapper;
import com.example.mybatisbasics.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        try {
            // SqlSessionFactoryの構築
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = 
                new SqlSessionFactoryBuilder().build(inputStream);

            // DB初期化
            try (SqlSession session = sqlSessionFactory.openSession()) {
                initializeDatabase(session);
                session.commit();
            }

            // CRUDテスト
            testCrud(sqlSessionFactory);

            // orders CRUDテスト
            testOrdersCrud(sqlSessionFactory);

            // 動的SQLテスト
            testDynamicSql(sqlSessionFactory);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testCrud(SqlSessionFactory sqlSessionFactory) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            // READ: 初期データ確認
            System.out.println("=== 初期データ ===");
            mapper.findAll().forEach(System.out::println);

            // CREATE: 新規登録
            System.out.println("\n=== CREATE ===");
            User newUser = new User();
            newUser.setName("新規ユーザー");
            newUser.setEmail("new@example.com");
            mapper.insert(newUser);
            System.out.println("登録したユーザー: " + newUser);

            // UPDATE: 更新
            System.out.println("\n=== UPDATE ===");
            newUser.setName("更新済みユーザー");
            mapper.update(newUser);
            User updated = mapper.findById(newUser.getId());
            System.out.println("更新後: " + updated);

            // DELETE: 削除
            System.out.println("\n=== DELETE ===");
            mapper.deleteById(newUser.getId());
            User deleted = mapper.findById(newUser.getId());
            System.out.println("削除後の検索結果: " + deleted);

            // emailが存在する場合はinsert をしない
            User newUser2 = new User();
            newUser2.setName("新規ユーザー2");
            newUser2.setEmail("tanaka@example.com");
            if (mapper.findByEmail(newUser2.getEmail()) == null) {
                mapper.insert(newUser2);
            };

            // 最終状態
            System.out.println("\n=== 最終データ ===");
            mapper.findAll().forEach(System.out::println);

            session.commit();
        }
    }
    private static void testOrdersCrud(SqlSessionFactory sqlSessionFactory) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            // READ: 初期データ確認
            System.out.println("=== 初期データ ===");
            mapper.findAll().forEach(System.out::println);

            // CREATE: 新規登録
            System.out.println("\n=== CREATE ===");
            Order newOrder = new Order();
            newOrder.setUserId(1L);
            newOrder.setProductName("新規注文");
            newOrder.setAmount(10000);
            mapper.insert(newOrder);
            System.out.println("登録した注文: " + newOrder);

            // UPDATE: 更新
            System.out.println("\n=== UPDATE ===");
            newOrder.setProductName("更新済み注文");
            mapper.update(newOrder);
            Order updated = mapper.findById(newOrder.getId());
            System.out.println("更新後: " + updated);

            // DELETE: 削除
            System.out.println("\n=== DELETE ===");
            mapper.deleteById(newOrder.getId());
            Order deleted = mapper.findById(newOrder.getId());
            System.out.println("削除後の検索結果: " + deleted);

            // DELETE by ids: 一括削除
            System.out.println("\n=== DELETE ===");
            List<Long> deletedIds = new ArrayList<>();
            deletedIds.add(1L);
            deletedIds.add(2L);
            mapper.deleteByIds(deletedIds);
            List<Order> findAll = mapper.findAll();
            String orders = findAll.stream()
                .map(s -> s.toString())
                .collect(Collectors.joining(","));
            System.out.println("一括削除後の検索結果: " + orders);


            // 最終状態
            System.out.println("\n=== 最終データ ===");
            mapper.findAll().forEach(System.out::println);

            session.commit();
        }
    }
    private static void testDynamicSql(SqlSessionFactory sqlSessionFactory) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            // 1. 条件検索（名前のみ）
            System.out.println("=== 名前で検索 ===");
            mapper.findByCondition("田中", null).forEach(System.out::println);

            // 2. 条件検索（メールのみ）
            System.out.println("\n=== メールで検索 ===");
            mapper.findByCondition(null, "@example").forEach(System.out::println);

            // 3. 複数IDで検索
            System.out.println("\n=== 複数IDで検索 ===");
            mapper.findByIds(Arrays.asList(1L, 2L)).forEach(System.out::println);

            // 4. ソート条件付き
            System.out.println("\n=== 名前でソート ===");
            mapper.findAllWithSort("name").forEach(System.out::println);

            // 5. 部分更新
            System.out.println("\n=== 部分更新（名前のみ） ===");
            User user = new User();
            user.setId(1L);
            user.setName("田中太郎（更新）");
            // emailはnullのままなので更新されない
            mapper.updateSelective(user);
            session.commit();
            System.out.println(mapper.findById(1L));

            // 6.ページネーション
            System.out.println("\n=== ページング ===");
            List<User> withPagination = mapper.findWithPagination(2, 1);
            withPagination.forEach(System.out::println);

            // 7.trimでORを実装
            System.out.println("\n=== trimでORを実装 ===");
            List<User> nameOrEmail = mapper.findNameOrEmail("鈴木", "tanaka");
            nameOrEmail.forEach(System.out::println);

            // 8.日付範囲検索
            System.out.println("\n=== 日付範囲検索 ===");
            LocalDateTime fromDateTime = LocalDateTime.of(2026, 1, 1, 0, 0);
            LocalDateTime toDateTime = LocalDateTime.of(2099, 12, 31, 0, 0);
            List<User> withinCreateAtRange = mapper.findWithinCreateAtRange(fromDateTime, toDateTime);
            withinCreateAtRange.forEach(System.out::println);
        }
    }
    private static void initializeDatabase(SqlSession session) throws IOException {
        // schema.sqlを実行
        try (Reader reader = new InputStreamReader(
                Resources.getResourceAsStream("schema.sql"), StandardCharsets.UTF_8)) {
            org.apache.ibatis.jdbc.ScriptRunner runner = 
                new org.apache.ibatis.jdbc.ScriptRunner(session.getConnection());
            runner.setLogWriter(null);
            runner.runScript(reader);
        }

        // data.sqlを実行
        try (Reader reader = new InputStreamReader(
                Resources.getResourceAsStream("data.sql"), StandardCharsets.UTF_8)) {
            org.apache.ibatis.jdbc.ScriptRunner runner = 
                new org.apache.ibatis.jdbc.ScriptRunner(session.getConnection());
            runner.setLogWriter(null);
            runner.runScript(reader);
        }
    }
}
