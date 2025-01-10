package me.parkhyejung.springbootdeveloper.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.domain.SiteUser;
import me.parkhyejung.springbootdeveloper.dto.AddUserRequest;
import me.parkhyejung.springbootdeveloper.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(AddUserRequest dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(SiteUser.builder()
                .email(dto.getEmail())
                // 패스워드 암호화 , 패스워드를 저장할때 시큐리티를 설정하며 패스워드 인코딩용으로 등록한 빈을 사용해서 암호화후에 저장
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    // 전달 받은 유저 ID 로 유저를 검색해서 전달하는 메서드
    public SiteUser findById (Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
    }

    public Optional<SiteUser> findByEmail (String email){
        return userRepository.findByEmail(email);
    }

    public SiteUser saveOAuth2User(String email, String provider) {
        SiteUser user = SiteUser.builder()
                .email(email)
                .password("") // OAuth2 사용자는 비밀번호가 필요하지 않음
                .nickname(email.split("@")[0]) // 이메일의 로컬 부분을 닉네임으로 사용
                .role("ROLE_USER") // 기본 역할 설정
                .provider(provider) // OAuth2 제공자 설정
                .build();
        return userRepository.save(user);
    }




}
