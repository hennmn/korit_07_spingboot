package com.example.todolist_project.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    static final long EXPIRATIONTIME = 8640000;  // 만료 시간 지정
    static final String PREFIX = "Bearer";

    // 비밀 키 생성.
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 서명이 이루어진 JWT 토큰을 생성
    public String getToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(key)
                .compact();
        return token;
    }

    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(token != null) {   // parser() 는 jjwt(jwt 라이브러리)
            String user = Jwts.parser()   // 토큰의 실제 헤더와 페이로드를 읽으려면 이게 필요함 // 파서 객체 준비
                    .setSigningKey(key)   // 서명 검증 키 설정
                    .build()  // 실제 파서 인스턴스 생성
                    .parseClaimsJws(token.replace(PREFIX,""))  // 이걸 쓰기 위해서 앞에 setSigningKey랑 build가 필요함
                    .getBody()    // 위의 검증 결과에서 페이로드만 가져오는 메서드(페이로드 JSON을 가져옴)
                                // 예 : { "sub": "username", "exp": 1234567890 }
                    .getSubject();


            if(user != null) {
                return user;
            }
        }
        return null;
    }
}
