package com.example.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * ユーザー関連の画面を制御するController
 */
@Controller
public class UserController {
    
    /**
     * トップページを表示
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Thymeleaf 基礎編へようこそ！");
        return "index";
    }
    
    /**
     * ユーザー一覧を表示
     */
    @GetMapping("/users")
    public String listUsers(Model model) {
        // サンプルデータを作成
        List<User> users = Arrays.asList(
            new User(1L, "田中太郎", "tanaka@example.com"),
            new User(2L, "佐藤花子", "sato@example.com"),
            new User(3L, "鈴木一郎", "suzuki@example.com")
        );
        
        // Modelにデータを追加
        model.addAttribute("users", users);
        model.addAttribute("totalCount", users.size());
        
        return "users/list";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        // idに該当するユーザーを探す処理
        List<User> users = Arrays.asList(
            new User(1L, "田中太郎", "tanaka@example.com"),
            new User(2L, "佐藤花子", "sato@example.com"),
            new User(3L, "鈴木一郎", "suzuki@example.com")
        );
        User targetUser = users.stream()
            .filter(user -> user.getId() == id)
            .findFirst()
            .orElse(null);
        
        // Modelにデータを追加
        model.addAttribute("user", targetUser);

        return "users/detail";
    }
}
