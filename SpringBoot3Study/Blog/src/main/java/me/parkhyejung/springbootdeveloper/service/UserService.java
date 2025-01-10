package me.parkhyejung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.domain.User;
import me.parkhyejung.springbootdeveloper.dto.AddUserRequest;
import me.parkhyejung.springbootdeveloper.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 정보 추가
    public Long save(AddUserRequest dto){
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword())) // 패스워드 암호화
                .role("ROLE_USER") // 일반 사용자는 기본적으로 ROLE_USER
                .provider("local") // 일반 로그인 사용자
                .build()).getId();
    }

    // 토큰 서비스 추가 , 전달 받은 유저 ID로 유저 검색해서 전달
    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("Unexcpected user"));
    }
    
    // 이메일을 입력 받아 users 테이블에서 유저를 찾고, 없으면 예외를 발생
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        // 비밀번호를 암호화한 뒤 저장
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User saveOAuth2User(String email, String provider) {
        User user = User.builder()
                .email(email)
                .password("") // OAuth2 사용자는 비밀번호가 필요하지 않음
                .nickname(email.split("@")[0]) // 이메일의 로컬 부분을 닉네임으로 사용
                .role("ROLE_USER") // 기본 역할 설정
                .provider(provider) // OAuth2 제공자 설정
                .build();
        return userRepository.save(user);
    }

}
