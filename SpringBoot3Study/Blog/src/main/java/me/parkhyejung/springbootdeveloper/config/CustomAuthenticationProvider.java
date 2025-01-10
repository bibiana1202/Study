package me.parkhyejung.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 인코더

    @Autowired
    public CustomAuthenticationProvider(@Lazy UserService userService, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 사용자가 입력한 이메일과 비밀번호를 가져옴
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 1. 사용자 조회
        var user = userService.findByEmail(email).orElseThrow(() ->
                new BadCredentialsException("Invalid email or password"));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        // 3. 인증 성공 시 UsernamePasswordAuthenticationToken 반환
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // UsernamePasswordAuthenticationToken 타입만 지원
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
