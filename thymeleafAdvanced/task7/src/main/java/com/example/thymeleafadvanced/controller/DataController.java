package com.example.thymeleafadvanced.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DataController {

    @GetMapping("/products")
    public String products(Model model) {
        List<Map<String, Object>> products = Arrays.asList(
            Map.of("id", 1, "name", "ノートPC", "price", 98000, "stock", 15),
            Map.of("id", 2, "name", "マウス", "price", 3500, "stock", 50),
            Map.of("id", 3, "name", "キーボード", "price", 8900, "stock", 30)
        );
        model.addAttribute("products", products);
        return "pages/demo/products";
    }

    @GetMapping("/chart")
    public String chart(Model model) {
        // 月別売上データ
        List<String> labels = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
        List<Integer> salesData = Arrays.asList(120, 190, 300, 250, 200, 350);
        
        model.addAttribute("chartLabels", labels);
        model.addAttribute("salesData", salesData);
        model.addAttribute("chartTitle", "月別売上推移");
        
        return "pages/demo/chart";
    }

    @GetMapping("/users-json")
    public String usersJson(Model model) {
        List<Map<String, Object>> users = Arrays.asList(
            Map.of("id", 1, "name", "山田太郎", "email", "yamada@example.com", "role", "ADMIN"),
            Map.of("id", 2, "name", "鈴木花子", "email", "suzuki@example.com", "role", "USER"),
            Map.of("id", 3, "name", "田中一郎", "email", "tanaka@example.com", "role", "USER")
        );
        model.addAttribute("users", users);
        return "pages/demo/users-json";
    }

    @GetMapping("/i18n")
    public String i18nDemo(Model model) {
        model.addAttribute("userName", "Thymeleaf学習者");
        model.addAttribute("itemCount", 5);
        return "pages/demo/i18n";
    }

    @GetMapping("/auth-status")
    public String authStatus() {
        return "pages/demo/auth-status";
    }
}
