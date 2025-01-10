package me.parkhyejung.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스가 기본키 값을 자동으로 생성하도록 지정하는 전략
    @Column(name="id", updatable = false) // 이 필드가 업데이트 불가능함
    private Long id;


    @Column(name="user_id", nullable=false, unique=true)
    private Long userId;

    @Column(name="refresh_token",nullable =false)
    private String refreshToken;

    public RefreshToken(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
        return this;
    }
}
