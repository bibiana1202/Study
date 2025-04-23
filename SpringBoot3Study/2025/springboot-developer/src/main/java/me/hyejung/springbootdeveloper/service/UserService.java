package me.hyejung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hyejung.springbootdeveloper.domain.User;
import me.hyejung.springbootdeveloper.dto.AddUserRequest;
import me.hyejung.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 저장
    public Long save(AddUserRequest dto){
        return  userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // 패스워드 암호화
                .build()
        ).getId();
    }

    // 전달받은 유저 ID로 유저를 검색해서 전달하는 메서드
    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }
}
