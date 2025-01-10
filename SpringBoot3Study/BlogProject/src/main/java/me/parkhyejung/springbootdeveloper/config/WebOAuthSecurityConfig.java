package me.parkhyejung.springbootdeveloper.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.config.jwt.TokenProvider;
import me.parkhyejung.springbootdeveloper.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import me.parkhyejung.springbootdeveloper.config.oauth.OAuth2SuccessHandler;
import me.parkhyejung.springbootdeveloper.config.oauth.OAuth2UserCustomService;
import me.parkhyejung.springbootdeveloper.repository.RefreshTokenRepository;
import me.parkhyejung.springbootdeveloper.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {
    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean
    public WebSecurityCustomizer configure(){ // 스프링 시큐리티 기능 비활성화
        return (web) -> web.ignoring()
                .requestMatchers(
                        new AntPathRequestMatcher("/img/**"),
                        new AntPathRequestMatcher("/css/**"),
                        new AntPathRequestMatcher("/js/**")
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 1. 토큰 방식으로 인증을 하기 때문에 기본에 사용하던 폼 로그인 ,세션 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // 사용자 정의 로그인 페이지 경로
                        .loginProcessingUrl("/login") // 로그인 요청 처리 URL
                        .defaultSuccessUrl("/articles", true) // 로그인 성공 후 이동할 경로
                        .failureUrl("/login?error=true") // 로그인 실패 시 이동할 경로
                        .permitAll() // 로그인 관련 페이지는 모두 접근 가능
                )
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // 필요 시에만 세션 생성

                // 2. 헤더를 확인할 커스텀 필터 추가
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                // 3. 토큰 재발급 URL 은 인증 없이 접근 가능하도록 설정. 나머지 API URL 은 인증 필요
                .authorizeRequests(auth->auth
                        .requestMatchers(new AntPathRequestMatcher("/api/token")).permitAll()  // 권한 필요 없음
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated() // 권한 필요
                        .anyRequest().permitAll()) // 나머지 권한 필요 없음


                // Spring의 OAuth2 로그인을 설정, OAuth2 클라이언트를 통해 사용자 인증을 처리
                .oauth2Login(oauth2->oauth2
                        // 로그인 페이지 URL 설정
                        .loginPage("/login")
                        // 4. Authorization 요청과 관련된 상태 저장 , OAuth2에 필요한 정보를 세션이 아닌 쿠키에 저장해서 쓸수 있도록 인증요청 관련된 상태를 저장할 저장소
                        .authorizationEndpoint(authorizationEndpoint-> authorizationEndpoint.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
                        // 사용자 정보 엔드포인트 설정 : 사용자 정보를 처리하는 서비스 설정, 이단계에서 사용자정보를 db에 연동하거나 추가 처리 할수 있다.
                        .userInfoEndpoint(userInfoEndpoint ->userInfoEndpoint.userService(oAuth2UserCustomService))
                        // 5. 인증 성공 시 실행할 핸들러 , 사용자가 인증후 특정 url로 리다이렉트하거나, jwt 토큰을 발급하는등의 작업을 수행
                        .successHandler(oAuth2SuccessHandler())        
                )

                // 6. /api 로 시작하는 url 인 경우 401 상태 코드를 반환하도록 예외 처리
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**")
                        ))

                // 로그아웃 설정 (컨트롤러에서 처리하도록 설정)
                //이 설정은 /logout을 직접 호출하지 않는 한, 자바스크립트 로그아웃 로직과 충돌하지 않습니다. 하지만 /logout을 호출하면 여전히 Spring Security의 기본 로그아웃 처리가 수행됩니다.
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 URL 설정
                        .logoutSuccessUrl("/login") // 로그아웃 성공 시 리다이렉트할 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID", "OAUTH2_AUTHORIZATION_REQUEST") // OAuth2 관련 쿠키도 삭제
                        .permitAll() // 로그아웃 경로에 대한 접근 허용
                )

                .build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler(){
        return new OAuth2SuccessHandler(tokenProvider,refreshTokenRepository,oAuth2AuthorizationRequestBasedOnCookieRepository(),userService);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository(){
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
