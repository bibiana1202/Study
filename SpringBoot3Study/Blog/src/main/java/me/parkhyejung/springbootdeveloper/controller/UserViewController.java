package me.parkhyejung.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        return "oauthlogin"; // 템플릿에서 일반 로그인 양식 및 OAuth2 로그인 링크를 모두 포함
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup"; // 일반 회원가입 양식
    }


}
