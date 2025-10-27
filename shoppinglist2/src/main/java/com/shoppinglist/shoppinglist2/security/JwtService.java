package com.shoppinglist.shoppinglist2.security;

import com.shoppinglist.shoppinglist2.entity.ShoppingItemRepository;
import com.shoppinglist.shoppinglist2.entity.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {
    static final long EXPIRATION_TIME = 86400000;
    static final String PREPIX = "Bearer ";

    // cardatabase에서 deprecated로 떠서 이렇게 수정했습니다.
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    // JWT 생성
    public String generateToken(String username) {
        String jwt = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
        return jwt;
    }

    // 요청 헤더에서 JWT 를 파싱하여 사용자 이름(subject) 추출
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(token != null && token.startsWith(PREPIX)) {
            try {
                String user = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token.replace(PREPIX, "")) // 접두사 "Bearer " 제거
                        .getPayload()
                        .getSubject();

                if(user != null) {
                    return user;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;   // getAuthUser 메서드 호출했는데 user 안 튀어나올 때 null return : 토큰이 없거나 유효하지 않음.
    }

}
