package com.example.thymeleafadvanced.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Layout Dialectでテンプレート継承を実装しています！");
        return "pages/home";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "pages/contact";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "pages/mypage";
    }
}
