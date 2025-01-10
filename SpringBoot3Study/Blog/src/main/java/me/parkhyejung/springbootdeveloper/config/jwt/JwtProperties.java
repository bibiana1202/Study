package me.parkhyejung.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // 자바 클래스에 프로퍼티 값을 가져와서 사용
public class JwtProperties {
    // JWT 토큰 값들을 변수로 접근하는데 사용할 JwtProperties 클래스
    // issuer필드에는 application 에서 설정한 jwt.issuer 값이 , secretKey 에는 jwt.secret_key 값이 매핑
    private String issuer;
    private String secretKey;
}
