package me.parkhyejung.springbootdeveloper.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.parkhyejung.springbootdeveloper.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;
    // 토큰을 생성하고, 올바른 토큰인지 유효성 검사를 하고, 토큰에서 필요한 정보를 가져오는 클래스

    // 1. JWT 토큰 생성 메서드
    public String generateToken(User user , Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()),user);
    }
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        log.debug("JWT 생성 - 사용자 ID: {}", user.getId());
        log.debug("JWT 생성 - 사용자 이메일: {}", user.getEmail());

        return Jwts.builder() //JWT의 정보를 설정하는 빌더 패턴을 사용
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE) // 헤더 typ : JWT
                .setIssuer(jwtProperties.getIssuer()) // 내용 iss : wew12025@gmail.com(properties 파일 에서 설정한 값)
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변숫값
                .setSubject(user.getEmail()) // 사용자 이메일 또는 ID를 subject로 설정
                .claim("id",user.getId())  // 클레임 id : 유저 ID
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .compact(); //JWT(Java Web Token)의 최종 문자열 형태로의 인코딩을 수행(JWT 토큰 문자열)
    }

    // 2. JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화, 복호화 과정에서 에러가 발생하면 유효하지 않은 토큰
                    .parseClaimsJws(token); // 주어진 token을 파싱하고 서명을 검증합니다.
            return true; // 예외가 발생하지 않으면 유효한 토큰
        }
        catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다. ExpiredJwtException: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("손상된 토큰입니다. MalformedJwtException: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("서명 검증 실패. SignatureException: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("잘못된 토큰입니다. IllegalArgumentException: {}", e.getMessage());
        }
        return false;
    }

    // 3. 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token); // 토큰에서 클레임 정보 추출

        log.debug("토큰에서 추출된 Claims: {}", claims);

        String username = claims.getSubject(); // 사용자 정보 (ex: 이메일)
        log.debug("사용자 이름(Subject): {}", username);

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("사용자 이름이 유효하지 않습니다.");
        }


        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")); // 사용자 권한 설정

        // UsernamePasswordAuthenticationToken 객체 생성
        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(),"",authorities),
                token,
                authorities);
    }

    // 토큰에서 클레임 정보를 추출하는 메서드 ( 클레임 : 내용의 한 덩어리)
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey()) // 비밀 키를 이용해 토큰을 복호화
                .parseClaimsJws(token) // 토큰 파싱 및 서명 검증
                .getBody(); // 클레임(Claims) 정보를 반환
    }

    // 4. 토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }
}
