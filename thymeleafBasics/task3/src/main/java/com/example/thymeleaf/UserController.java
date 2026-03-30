package com.example.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ユーザー関連の画面を制御するController
 */
@Controller
public class UserController {

    // 登録されたユーザーを保持（本来はデータベースに保存）
    private List<User> userList = new ArrayList<>(Arrays.asList(
        new User(1L, "田中太郎", "tanaka@example.com"),
        new User(2L, "佐藤花子", "sato@example.com"),
        new User(3L, "鈴木一郎", "suzuki@example.com")
    ));
    private Long nextId = 4L;

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
        model.addAttribute("users", userList);
        model.addAttribute("totalCount", userList.size());
        return "users/list";
    }
    

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        // idに該当するユーザーを探す処理
        User targetUser = userList.stream()
            .filter(user -> user.getId() == id)
            .findFirst()
            .orElse(null);
        
        // Modelにデータを追加
        model.addAttribute("user", targetUser);

        return "users/detail";
    }

    /**
     * ユーザー登録フォームを表示
     */
    @GetMapping("/users/new")
    public String showCreateForm(Model model) {
        // 空のフォームオブジェクトをModelに追加
        model.addAttribute("userForm", new UserForm());
        return "users/form";
    }
    
    /**
     * ユーザー登録を処理
     */
    @PostMapping("/users")
    public String createUser(
            @Valid @ModelAttribute("userForm") UserForm form,
            BindingResult bindingResult,
            Model model) {
        
        // バリデーションエラーがある場合はフォームを再表示
        if (bindingResult.hasErrors()) {
            return "users/form";
        }
        
        // 新しいユーザーを作成して保存
        User newUser = new User(nextId++, form.getName(), form.getEmail());
        userList.add(newUser);
        
        // 一覧ページにリダイレクト
        return "redirect:/users";
    }
}
