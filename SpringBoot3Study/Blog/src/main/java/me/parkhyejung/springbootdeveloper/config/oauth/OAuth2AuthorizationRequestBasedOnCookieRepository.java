package me.parkhyejung.springbootdeveloper.config.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.parkhyejung.springbootdeveloper.util.CookieUtil;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

// OAuth2에 필요한 정보를 세션이 아닌 쿠키에 저장해서 쓸 수 있도록 인증 요청과 관련된 상태를 저장할 저장소
// 권한 인증 흐름에서 클라이언트의 요청을 유지하는데 사용하는 AuthorizationRequestRepository 클래스
// 를 구현해서 쿠키를 사용해 OAuth의 정보를 가져오고 저장하는 로직
public class OAuth2AuthorizationRequestBasedOnCookieRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME ="oauth2_auth_request"; // OAuth2 인증 요청 정보를 저장하는 쿠키 이름 지정
    private final static int COOKIE_EXPIRE_SECONDS = 18000; // 쿠키 유효시간

    @Override // 쿠키에 저장된 OAuth2 인증 요청 정보 제거
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    @Override // 요청에서 쿠키를 찾아서 OAuth2 인증 요청 정보 로드
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME); // 쿠키 가져옴
        return CookieUtil.deserialize(cookie,OAuth2AuthorizationRequest.class); // 쿠키값 객체로 역직렬화
    }

    @Override // OAuth2 인증요청 정보를 쿠키에 저장
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if(authorizationRequest == null) {
            removeAuthorizationRequestCookies(request,response); //쿠키 삭제
            return;
        }
        CookieUtil.addCookie(response,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,CookieUtil.serialize(authorizationRequest),COOKIE_EXPIRE_SECONDS); // 쿠키추가, 직렬화
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request,response,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME); //쿠키 삭제
    }
}
