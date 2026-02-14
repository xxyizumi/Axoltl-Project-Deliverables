package com.example.mybatisbasics;

import com.example.mybatisbasics.entity.User;
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
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // 1. SqlSessionFactoryの構築
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = 
                new SqlSessionFactoryBuilder().build(inputStream);

            // 2. SqlSessionの取得とDB初期化
            try (SqlSession session = sqlSessionFactory.openSession()) {
                // スキーマとデータを初期化
                initializeDatabase(session);
                session.commit();
            }

            // 3. SqlSessionでMapperを使用
            try (SqlSession session = sqlSessionFactory.openSession()) {
                UserMapper userMapper = session.getMapper(UserMapper.class);

                // 全件取得
                System.out.println("=== 全ユーザー取得 ===");
                List<User> users = userMapper.findAll();
                users.forEach(System.out::println);

                // ID検索
                System.out.println("\n=== ID=1のユーザー ===");
                User user = userMapper.findById(1L);
                System.out.println(user);

                // メールアドレス検索
                System.out.println("\n=== email=sato@example.comのユーザー ===");
                User targetEmailUser = userMapper.findByEmail("sato@example.com");
                System.out.println(targetEmailUser);

                // ユーザーの総数取得
                System.out.println("\n=== ユーザーの総数 ===");
                int userCount = userMapper.count();
                System.out.println(userCount);

                // ユーザ名の部分一致検索
                System.out.println("\n=== ユーザ名に木が含まれるユーザ ===");
                User userbyNameLike = userMapper.findByNameLike("木");
                System.out.println(userbyNameLike);
            }

        } catch (IOException e) {
            e.printStackTrace();
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
