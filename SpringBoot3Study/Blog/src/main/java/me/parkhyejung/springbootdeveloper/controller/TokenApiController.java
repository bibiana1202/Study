package me.parkhyejung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.dto.CreateAccessTokenRequest;
import me.parkhyejung.springbootdeveloper.dto.CreateAccessTokenResponse;
import me.parkhyejung.springbootdeveloper.service.TokenService;
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
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request){

        try {
            String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CreateAccessTokenResponse(newAccessToken));
        } catch (IllegalArgumentException  e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateAccessTokenResponse("Invalid refresh token"));
        }
    }
}
