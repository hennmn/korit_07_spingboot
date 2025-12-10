package com.shoppinglist.shoppinglist2.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {
    static final long EXPIRATION_TIME = 86400000;
    static final String PREFIX = "Bearer ";

    // cardatabase에서 deprecated로 떠서 이렇게 수정했습니다.
    // private final SecretKey key = Jwts.SIG.HS256.key().build();
    static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // JWT 생성
    public String generateToken(String username) {
        String jwt = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
        return jwt;
    }

    // 요청 헤더에서 JWT를 파싱하여 사용자 이름(subject) 추출
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null && token.startsWith(PREFIX)) {
            try {
                String user = Jwts.parser()
                        .verifyWith(key) // 비밀 키로 검증
                        .build()
                        .parseSignedClaims(token.replace(PREFIX, "")) // 접두사 "Bearer " 제거
                        .getPayload()
                        .getSubject();

                if (user != null) {
                    return user;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null; // getAuthUser 메서드 호출했는데 user 안튀어나올 때 null return : 토큰이 없거나 유효하지 않음.
    }
}
// package com.shoppinglist.shoppinglist2.security;
//
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;
// import jakarta.servlet.http.HttpServletRequest; // 사용하지 않으므로 제거 가능
// import lombok.extern.slf4j.Slf4j; // Slf4j 로거 추가
// import org.springframework.http.HttpHeaders; // 사용하지 않으므로 제거 가능
// import org.springframework.stereotype.Component;
//
// import javax.crypto.SecretKey;
// import java.time.Instant;
// import java.time.temporal.ChronoUnit;
// import java.util.Date;
//
// @Component
// @Slf4j // 로거 사용
// public class JwtService {
// // 만료 시간 (1일)
// static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 1 day in ms
// // JWT 서명에 사용할 Secret Key (애플리케이션 실행 시 한 번만 생성되어야 함)
// // static final 로 선언하여 클래스 로딩 시점에 키가 고정되도록 함
// static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //
// HS256 알고리즘용 키 자동 생성
//
// // JWT 생성
// public String generateToken(String username) {
// Instant now = Instant.now();
// String token = Jwts.builder()
// .setSubject(username)
// .setIssuedAt(Date.from(now))
// .setExpiration(Date.from(now.plus(EXPIRATION_TIME, ChronoUnit.MILLIS)))
// .signWith(key) // 생성된 키로 서명
// .compact();
// log.debug("[JwtService] Token generated for user {}: {}", username,
// token.substring(0, 10) + "...");
// return token;
// }
//
// // 토큰 검증 및 사용자 이름(Subject) 추출
// public String getAuthUser(String token) {
// if (token == null || token.isEmpty()) { // 빈 문자열 체크 추가
// log.warn("[JwtService] getAuthUser called with null or empty token.");
// return null;
// }
// try {
// log.debug("[JwtService] Attempting to parse token: {}", token.substring(0,
// Math.min(token.length(), 10)) + "...");
// Claims claims = Jwts.parser()
// .setSigningKey(key) // 토큰 생성 시 사용한 키와 동일한 키로 검증
// .build()
// .parseClaimsJws(token)
// .getBody();
//
// String username = claims.getSubject();
// log.debug("[JwtService] Token validation successful for subject: {}",
// username);
// return username; // Subject (사용자 이름) 반환
//
// } catch (io.jsonwebtoken.ExpiredJwtException e) {
// // --- 토큰 만료 시 로그 ---
// log.warn("[JwtService] Token validation failed: Expired token - {}",
// e.getMessage());
// } catch (io.jsonwebtoken.security.SignatureException e) {
// // --- 서명 불일치 시 로그 ---
// log.warn("[JwtService] Token validation failed: Invalid signature - {}",
// e.getMessage());
// } catch (io.jsonwebtoken.MalformedJwtException e) {
// // --- 토큰 형식 오류 시 로그 ---
// log.warn("[JwtService] Token validation failed: Malformed token - {}",
// e.getMessage());
// } catch (io.jsonwebtoken.UnsupportedJwtException e) {
// // --- 지원하지 않는 형식의 토큰 시 로그 ---
// log.warn("[JwtService] Token validation failed: Unsupported token - {}",
// e.getMessage());
// } catch (IllegalArgumentException e) {
// // --- 잘못된 인자 전달 시 로그 ---
// log.warn("[JwtService] Token validation failed: Illegal argument - {}",
// e.getMessage());
// } catch (io.jsonwebtoken.JwtException e) { // 기타 JWT 관련 예외
// log.warn("[JwtService] Token validation failed: {} - {}",
// e.getClass().getSimpleName(), e.getMessage());
// } catch (Exception e) { // 그 외 예외
// log.error("[JwtService] Unexpected error during token validation: {}",
// e.getMessage(), e);
// }
// // --- 검증 실패 시 null 반환 ---
// log.warn("[JwtService] Returning null due to token validation failure.");
// return null;
// }
// }
//