package me.parkhyejung.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@RequiredArgsConstructor

public class WebSecurityConfig {

    private final UserDetailService userService;

    // 스프링 시큐리티 기능 비활성화 : 스프링 시큐리티의 모든 기능을 사용하지 않게 설정하는 코드.
    // 즉, 인증,인가 서비스를 모든곳에 적용하지 않습니다.
    // 일반적으로 정적 리소스(이미지,HTML 파일)에 설정. 정적 리소스만 스프링 시큐리티 사용을 비활성화 하는데
    // static 하위 경로에 있는 리소스와 h2의 데이터를 확인하는데 사용하는 h2-console 하위 url 을 대상으로 ignoring() 메서드를 사용
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    // 이 메서드에서 인증/인가 및 로그인,로그아웃 설정
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        return http
//                .authorizeRequests(auth ->auth // 인증, 인가 설정
//                        .requestMatchers(
//                                new AntPathRequestMatcher("/login"),
//                                new AntPathRequestMatcher("/signup"),
//                                new AntPathRequestMatcher("/user")
//                        ).permitAll()
//                        .anyRequest().authenticated())
//                .formLogin(formLogin->formLogin //폼 기반 로그인 설정 , 특정 경로에대한 엑세스 설정을 함.
//                .loginPage("/login") // 로그인 페이지 경로를 설정
//                .defaultSuccessUrl("/articles") // 로그인이 완료되었을 때 이동할 경로를 설정
//                )
//                .logout(logout -> logout  // 로그아웃 설정
//                        .logoutSuccessUrl("/login") // 로그아웃이 완료되었을때 이동할 경로를 설정
//                        .invalidateHttpSession(true)  // 로그아웃 이후에 세션을 전체 삭제할지 여부를 설정
//                )
//                .csrf(AbstractHttpConfigurer::disable)  // csrf 비활성화, csrf 공격을 방지하기 위해서는 활성화하는게 좋다
//                .build();
        return http
                .authorizeRequests()
                .requestMatchers("/login", "/signup", "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/articles")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable()
                .build();
    }

    // 인증 관리자 관련 설정. 사용자 정보를 가져올 서비스를 재정의 하거나, 인증방법, 예를들어 LDAP,JDBC 기반 인증 등을 설정할때 사용
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,UserDetailService userDetailService) throws Exception{
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService); // 사용자 정보 서비스 설정
//        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
//        return new ProviderManager(authProvider);
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
