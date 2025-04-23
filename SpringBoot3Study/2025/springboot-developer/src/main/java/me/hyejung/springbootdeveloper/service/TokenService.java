package me.hyejung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hyejung.springbootdeveloper.config.jwt.TokenProvider;
import me.hyejung.springbootdeveloper.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken){
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }
        // 유효한 토큰일때 리프레시 토큰으로 사용자 ID 를 찾는다.
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        // 사용자 ID 로 사용자를 찾는다.
        User user = userService.findById(userId);
        // 새로운 액세스 토큰을 생성한다.
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
