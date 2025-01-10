//package me.parkhyejung.springbootdeveloper.config;
//
//import lombok.RequiredArgsConstructor;
//import me.parkhyejung.springbootdeveloper.config.jwt.TokenProvider;
//import me.parkhyejung.springbootdeveloper.service.UserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration // 스프링의 환경 설정 파일
//@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션 , 스프링 시큐리티를 활성화 하는 역할
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//    private final TokenProvider tokenProvider; // TokenProvider 의존성 주입
//
//    // 1. 스프링 시큐리티 기능 비활성화
//    @Bean
//    public WebSecurityCustomizer configure(){
//        return (web) -> web.ignoring()
//                .requestMatchers(new AntPathRequestMatcher("/static/**"));
//    }
//
//    // 2. 특정 HTTP 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth->auth.requestMatchers(
//                    new AntPathRequestMatcher("/login"),
//                    new AntPathRequestMatcher("/signup"),
//                    new AntPathRequestMatcher("/user"),
//                    new AntPathRequestMatcher("/api/token","POST") // 여기에 `/api/token` 경로 접근 허용 추가
//                ).permitAll().anyRequest().authenticated()) // 3. 인증, 인가 설정
//                .formLogin(formLogin -> formLogin.loginPage("/login").defaultSuccessUrl("/articles")) // 4. 폼 기반 로그인 설정
//                .logout(logout->logout.logoutSuccessUrl("/login").invalidateHttpSession(true)) // 5. 로그아웃 설정
//                .csrf(AbstractHttpConfigurer::disable) // 6. csrf 비활성화
//                ;
//
//        // 커스텀 인증 필터 추가
//        http.addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//
//    }
//
//    // 7. 인증 관리자 관련 설정
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailService); // 8. 사용자 정보 서비스 설정,UserDetailsService 등록
//        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
//        return new ProviderManager(authProvider);
//    }
//
//    // 9. 패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
