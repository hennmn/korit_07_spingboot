package com.example.shoppinglist;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    // 필터나 시큐리티가 “인증 실패”를 감지했을 때 이 commence()가 호출됩니다.
    //여기서 JSON으로 에러 메시지를 만들어 내려주거나, 상태 코드를 401/403으로 설정할 수 있습니다.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // SC_UNAUTHORIZED는 HTTP 401 상태 코드 => Unauthorized(인증되지 않았습니다.)  (즉, 로그인 정보가 없거나 잘못된 경우에 사용됩니다.)
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);  // 브라우저나 클라이언트에게 "이 응답은 JSON 타입이야" 라고 알려주는 부분입니다.
        PrintWriter writer = response.getWriter();
        writer.println("Error : wrong id or password" + authException.getMessage());   // 이걸 JSON형식으로 보낸다는 거임
        // {"Error": "wrong id or password"}
    }
}