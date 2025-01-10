package me.parkhyejung.springbootdeveloper.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.parkhyejung.springbootdeveloper.config.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Log4j2
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    // JWT 토큰을 이용한 인증 필터를 구현하는 클래스
    // Spring Security 와 함께 사용되어, 요청이 올때 마다 사용자의 토큰을 검사하고 인증 정보를 설정해줍니다, 모든 요청마다 한번만 실행되는 필터
    // JWT 토큰을 이용하여 인증된 사용자 정보를 설정하고 ,이후의 요청이 인증된 상태로 처리될 수 있도록 합니다.
    private final TokenProvider tokenProvider;  // 서비스
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 필터의 주요 로직을 처리
        // HTTP 요청이 들어올 때 마다 호출되며 ,토큰을 검사하고, 유효한 경우 인증 정보를 설정 합니다.

        // 요청 헤더의 Authorization 키의 값 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        log.debug("Authorization 헤더 값: {}", authorizationHeader);

        // 가져온 값에서 접두사 제거
        String token = getAccessToken(authorizationHeader);
        log.debug("Extracted 토큰 값: {}", token);

        try{
            // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보 설정
            if(token != null && tokenProvider.validToken(token)) { // 토큰의 유효성 검사
                Authentication authentication = tokenProvider.getAuthentication(token); // 사용자의 인증정보 가져옵니다.
                log.debug("인증 정보: {}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 정보를 SecurityContext에 설정
                // 이 과정이 완료되면, 이후의 요청은 인증된 사용자가 된 것 입니다.
            } else{
                log.warn("유효하지 않은 토큰입니다. Token: {}", token);
            }
        }
        catch (Exception e) {
            log.error("토큰 인증 과정 중 오류 발생: {}", e.getMessage(), e);

            // 로그 기록 및 응답 상태 설정
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
            return; // 필터 체인 실행 중단
        }

        // 다음 필터로 요청 전달
        try {
            filterChain.doFilter(request, response); // 인증 필터링이 끝난후, 요청을 다음 필터로 전달합니다. 이를 통해 정상적인 응답처리가 가능해 집니다.
        } catch (Exception e) {
            log.error("필터 체인에서 오류 발생: {}", e.getMessage(), e);
        }finally {
            SecurityContextHolder.clearContext();
        }
        // 필터 체인 내에서 다음 필터를 호출하는 메서드로서, 이를 통해 연속적으로 필터들이 순차적으로 요청을 처리할 수 있도록 해줍니다. 이를 호출하지 않으면 필터 체인의 처리가 중단되고, 요청이 최종 서블릿까지 도달하지 못할 수 있습니다.
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
