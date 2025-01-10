package me.parkhyejung.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        System.out.println("Custom login page requested"); // 디버깅 로그
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
}
