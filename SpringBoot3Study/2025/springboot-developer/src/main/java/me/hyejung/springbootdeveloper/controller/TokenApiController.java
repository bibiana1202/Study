package me.hyejung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hyejung.springbootdeveloper.dto.CreateAccessTokenRequest;
import me.hyejung.springbootdeveloper.dto.CreateAccessTokenResponse;
import me.hyejung.springbootdeveloper.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request){

        // 리프레시 토큰을 기반으로 새로운 액세스 토큰을 만들어 준다.
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

}
