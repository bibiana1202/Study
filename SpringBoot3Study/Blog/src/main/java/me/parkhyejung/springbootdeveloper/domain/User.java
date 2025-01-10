package me.parkhyejung.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name="users")
@NoArgsConstructor
@Getter @Setter
@Entity
public class User implements UserDetails { // UserDetails를 상속받아 인증 객체로 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name="email",nullable = false,unique = true)
    private String email;

    @Column(name="password")
    private String password;

    // 사용자 이름
    @Column(name="nickname",unique = true)
    private String nickname;

    @Column(name = "role", nullable = false)
    private String role; // ROLE_USER, ROLE_ADMIN, ROLE_OAUTH 등

    @Column(name = "provider", nullable = false)
    private String provider; // "local", "google", "github" 등


    @Builder
    public User(String email, String password,String nickname,String role, String provider) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role != null ? role : "ROLE_USER";
        this.provider = provider != null ? provider : "local";
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("user"));
        return List.of(new SimpleGrantedAuthority(this.role));

    }

    // 사용자의 id를 반환(고유한값)
    @Override
    public String getUsername() {
//        return password;
        return this.email;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용가능
    }

    // 사용자 이름 변경
    public User update(String nickname) {
        this.nickname = nickname;
        return this;
    }
}
