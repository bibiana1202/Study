package me.parkhyejung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.config.jwt.TokenProvider;
import me.parkhyejung.springbootdeveloper.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;


    // 전달받은 리프레시 토큰으로 토큰 유효성 검사를 진행하고
    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // 유효한 토큰일때 리프레시 토큰으로 사용자 id 를 찾습니다.
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);
        // 사용자 id로 사용자를 찾은 후에 토큰 제공자의 generateToken 메서드를 호출하여 새로운 액세스 토큰을 생성합니다.
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
