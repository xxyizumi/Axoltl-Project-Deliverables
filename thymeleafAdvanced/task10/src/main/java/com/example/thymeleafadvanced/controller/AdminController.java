package com.example.thymeleafadvanced.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Layout Dialectでテンプレート継承を実装しています！");
        return "pages/admin/home";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/admin/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "pages/admin/contact";
    }
}
