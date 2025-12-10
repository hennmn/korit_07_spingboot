package com.example.todolist_project;

import com.example.todolist_project.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jws = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jws != null) {
            String user = jwtService.getAuthUser(request);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 현재 요청의 시큐리티 컨텍스트에 이 인증 객체를 저장.
        }
        filterChain.doFilter(request, response);   // 여기서 LoginController로 요청이 그래로 전달됨(@RestController)

        // 이렇게 하면 컨트롤러를 "이미 인증된 사용자 정보를 바로 저장할 수 있고,
        // 인ㅇ증 실패시에는 컨트롤러까지 가지 않고 AuthEntryPoint(예외 처리)에서 401/403을 내려보낼 수 있음
    }
}
