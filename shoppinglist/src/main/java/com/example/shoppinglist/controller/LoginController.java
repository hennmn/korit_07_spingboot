package com.example.shoppinglist.controller;

import com.example.shoppinglist.domain.AccountCredentials;
import com.example.shoppinglist.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        // user 인증
        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        Authentication auth = authenticationManager.authenticate(creds);    // 로그인시에 들어온 user정보와 실제 DB에 있는 user정보를 여기서 비교

        String jwts = jwtService.getToken(auth.getName());   // 인증이 완료된 정보로 토큰 생성

        // 생성된 토큰으로 응답을 build()
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + jwts)   // JWT 토큰을 Authorization 헤더에 담음
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")  // JS 코드가 Authorization 헤더를 읽을 수 있도록 허용
                .build();

        /*
            Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

            이건 key : value 형태예요.
            key → "Authorization"
            value → "Bearer " + 실제 토큰 문자열

            Spring의 ResponseEntity는 헤더를 추가할 때 이렇게 씁니다:
            .header(String name, String value)

         */
    }





}
