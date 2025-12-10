package com.example.todolist_project;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthEntryPoint implements AuthenticationEntryPoint {
    // 필터나 시큐리티가 "인증 실패"를 감지했을 때 이 commence()가 호출됩니다.
    // 여기서 JSON으로 에러 메시지를 만들어 내려주거나, 상태 코드를 401/403 으로 설정할 수 있습니다.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 응답의 HTTP 상태 코드를 401로 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.println("Error : worong id or password" + authException.getMessage());



        /*
            401은 "이 리소스는 인증이 필요한데, 네 요청에는 유효한 정보가 없다."라는 뜻(로그인(혹은 토큰) 이 없거나, 토큰이 만료/손상/형식오류일 때 쓰는 코드)
            403 Forbidden: 인증은 됐지만 권한이 부족함. → “넌 이 작업을 할 권한이 없어.
         */
    }
}
