package me.parkhyejung.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.parkhyejung.springbootdeveloper.domain.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AddUserRequest {
    // 회원가입 구현 - 사용자 정보를 담고있는 객체
    private String email;
    private String password;

    private String nickname;

    // User 객체로 변환하며 role과 provider 지정
    public User toUser(String role, String provider) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(role)
                .provider(provider)
                .build();
    }

}
