package me.parkhyejung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.dto.CreateAccessTokenRequest;
import me.parkhyejung.springbootdeveloper.dto.CreateAccessTokenResponse;
import me.parkhyejung.springbootdeveloper.service.RefreshTokenService;
import me.parkhyejung.springbootdeveloper.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    // 새로운 엑세스 토큰 발급
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
    }

    // 리프레시 토큰 삭제 API
    @DeleteMapping("/api/refresh-token")
    public ResponseEntity deleteRefreshToken(){
        refreshTokenService.delete();
        return ResponseEntity.ok().build();
    }
}
