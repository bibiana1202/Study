package me.parkhyejung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.domain.User;
import me.parkhyejung.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
// 스프링 시큐리티에서 사용자 정보를 가져오는 인터페이스
/**
 * UserDetailsService 실행 시점
 * UserDetailsService는 사용자가 로그인 요청을 보낼 때 실행됩니다.
 *
 * 1. 사용자가 로그인 요청을 보냅
 * 2. UsernamePasswordAuthenticationFilter 에서 인증 시작
 * 3. AuthenticationManager 호출 : AuthenticationManager는 적절한 AuthenticationProvider를 찾아 실행
 * 4. AuthenticationProvider가 UserDetailsService 호출
 * 5. loadUserByUsername 메서드 실행
 * 6. 인증 성공 여부 판단 : 반환된 UserDetails 객체에서 password를 가져와 사용자가 입력한 비밀번호와 비교(PasswordEncoder를 사용하여 이루어짐)
 */
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 사용자 이름(email)으로 사용자의 정보를 가져오는 메소드
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .orElseThrow(()->new IllegalArgumentException((email)));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // 암호화된 비밀번호
                .roles(user.getRole()) // ROLE_USER 등 역할 설정
                .build();


    }
}
