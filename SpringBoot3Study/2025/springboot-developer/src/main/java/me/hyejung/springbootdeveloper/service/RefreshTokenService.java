package me.hyejung.springbootdeveloper.service;


import lombok.RequiredArgsConstructor;
import me.hyejung.springbootdeveloper.domain.RefreshToken;
import me.hyejung.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // 전달받은 리프레시 토큰으로 리프레시 토큰 객체를 검색해서 전달
    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected token"));
    }
}
