package me.parkhyejung.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.config.jwt.TokenProvider;
import me.parkhyejung.springbootdeveloper.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import me.parkhyejung.springbootdeveloper.config.oauth.OAuth2SuccessHandler;
import me.parkhyejung.springbootdeveloper.config.oauth.OAuth2UserCustomService;
import me.parkhyejung.springbootdeveloper.repository.RefreshTokenRepository;
import me.parkhyejung.springbootdeveloper.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {
    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService; // @Lazy 추가
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public WebOAuthSecurityConfig(
            OAuth2UserCustomService oAuth2UserCustomService,
            TokenProvider tokenProvider,
            RefreshTokenRepository refreshTokenRepository,
            @Lazy UserService userService, // 지연 초기화
            @Lazy CustomAuthenticationProvider customAuthenticationProvider // 지연 초기화
    ) {
        this.oAuth2UserCustomService = oAuth2UserCustomService;
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }


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
        // 1. 토큰 방식으로 인증 하기 때문에 기존에 사용하던 폼 로그인, 세션 비활성화
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // 사용자 정의 로그인 페이지 경로
                        .loginProcessingUrl("/login") // 로그인 요청 처리 URL
                        .defaultSuccessUrl("/articles", true) // 로그인 성공 후 이동할 경로
                        .failureUrl("/login?error=true") // 로그인 실패 시 이동할 경로
                        .permitAll() // 로그인 관련 페이지는 모두 접근 가능
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 URL
                        .logoutSuccessUrl("/login?logout=true") // 로그아웃 성공 시 이동할 경로
                        .permitAll() // 로그아웃도 접근 가능
                ).sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                // 2. 헤더를 확인할 커스텀 필터 추가
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 3. 토큰 재발급 URL 은 인증 없이 접근 가능하도록 설정. 나머지 API URL 은 인증 필요
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/api/token")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
                        .anyRequest().permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        // 4. Authorization 요청과 관련된 상태 저장
                        .authorizationEndpoint(authorizationEndpoint->authorizationEndpoint.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(oAuth2UserCustomService))
                        // 5. 인증 성공시 실행할 핸들러
                        .successHandler(oAuth2SuccessHandler())
                )
                // api 로 시작하는 url 인 경우 인증실패시 401 상태 코드를 반환하도록 예외 처리
                .exceptionHandling(exceptionHandling->exceptionHandling
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**")
                        ))
                .build();
    }

    // 인증 성공시 실행할 핸들러
    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler(){
        return new OAuth2SuccessHandler(tokenProvider,
            refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
            userService);
    }

    // 헤더를 확인할 커스텀 필터
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }




}
