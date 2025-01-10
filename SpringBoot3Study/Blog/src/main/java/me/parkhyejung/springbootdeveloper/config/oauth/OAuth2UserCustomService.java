package me.parkhyejung.springbootdeveloper.config.oauth;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.domain.User;
import me.parkhyejung.springbootdeveloper.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@RequiredArgsConstructor
@Service
// OAuth2 인증을 사용하여 소셜로그인을 처리하는 클래스
// OAuth2 인증후 사용자 정보를 불러오는 기본 서비스 를 커스텀
public class OAuth2UserCustomService extends DefaultOAuth2UserService{
    private final UserRepository userRepository;
    
    @Override // Spring Security 에서 OAuth2 인증이 성공하면 호출되는 메서드!!
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest); // OAuth2 인증요청(userRequest)에 따라 사용자 정보 로드
        saveOrUpdate(user); // 가져온 사용자 정보를 기반으로 db에 사용자를 저장하거나 업데이트
        return user;
    }

    // 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdate(OAuth2User oAuth2User){
        Map<String,Object> attributes= oAuth2User.getAttributes(); // OAuth2 공급자로부터 가져온 사용자 정보를 맵형태로 추출
        String email=(String) attributes.get("email");
        String name = (String) attributes.get("name");
        User user =userRepository.findByEmail(email) // db 에서 이메일 기반으로 사용자 검색
                .map(entity -> entity.update(name)) // 사용자가 이미 존재하는 경우 이름(닉네임)을 업데이트
                .orElse(User.builder() // 사용자가 존재하지 않을 경우 새로 생성
                        .email(email)
                        .nickname(name)
                        .build());
        return userRepository.save(user);
    }

}
