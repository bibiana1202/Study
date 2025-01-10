package me.parkhyejung.springbootdeveloper.config;

import me.parkhyejung.springbootdeveloper.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AuthenticationProviderConfig {

    private final UserService userService; // @Lazy 추가
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationProviderConfig(
            @Lazy UserService userService, // 지연 초기화
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }



    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userService, passwordEncoder);
    }
}
