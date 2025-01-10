package me.parkhyejung.springbootdeveloper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.dto.AddUserRequest;
import me.parkhyejung.springbootdeveloper.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/user")
    public String signup(AddUserRequest request){
        // 회원가입 시 role과 provider 값을 지정
        userService.save(request.toUser("ROLE_USER", "local"));
        return "redirect:/login"; // 회원 가입이 완료된 이후 로그인 페이지로 이동
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder.getContext().getAuthentication());

        // 추가적으로 OAuth2 관련 세션 정리 로직 (필요한 경우)
        // request.getSession().invalidate();

        return "redirect:/login";

    }

}
