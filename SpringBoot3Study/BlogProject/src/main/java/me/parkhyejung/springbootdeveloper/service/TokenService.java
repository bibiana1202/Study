package me.parkhyejung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.config.jwt.TokenProvider;
import me.parkhyejung.springbootdeveloper.domain.SiteUser;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    // 전달받은 리프레시 토큰으로 토큰 유효성 검사를 진행하고, 유효한 토큰일때 리프레시 토큰으로 사용자ID를 찾고, 토큰 제공자의 generateToken 호출해서 새로운 액세스 토큰을 생성
    public String createNewAccessToken(String refreshToken){
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Invalid refresh token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        SiteUser user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
