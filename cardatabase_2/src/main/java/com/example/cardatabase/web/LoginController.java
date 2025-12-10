package com.example.cardatabase.web;

import com.example.cardatabase.domain.AccountCredentials;
import com.example.cardatabase.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final JwtService jwtService;  // 토큰
    private final AuthenticationManager authenticationManager;    // Spring Security 프레임워크에서 제공하는 인터페이스
                                                            // 사용자가 입력한 아이디(username), 비밀번호(password) 를 검증해서 "정상 사용자냐 아니냐"를 판단하는 역할을 맡아요.

    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        // 여기에 토큰 생성하고 응답의 Authorization 헤더로 전송해주는 로직 작성할겁니다.
        UsernamePasswordAuthenticationToken creds =   // 사용자가 입력한 아이디/비번을 담은 인증 요청 객체
                new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password()); // AccountCredentials에서 record써서 그럼 record는 getter setter가 자동 생성되기 때문에 get.username()이 아니라 credentials.username()임

        Authentication auth = authenticationManager.authenticate(creds);

        // 토큰 생성 - jwts를 지역변수로 보셔도 무방하죠
        String jwts = jwtService.getToken(auth.getName());

        // 생성된 토큰으로 응답을 빌드
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .build();  // .build()는 응답 body가 필요없을 때 사용  // HTTP 200 OK + 헤더만 있는 응답

    }
}
