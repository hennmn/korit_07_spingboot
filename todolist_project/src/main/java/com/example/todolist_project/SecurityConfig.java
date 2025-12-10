package com.example.todolist_project;

import com.example.todolist_project.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;

    /*
        빌더 내부에 여러 개의 AuthenticationProvider(인증 전략)을 등록해두고,
        마지막에 들을 묶어 AuthenticationManager를 만들어 냅니다.

        로그인 시에 authenticationManager.authentication(new UsernamePasswordAuthenticationToken(...))rk
        호출되면, 내부의 Provider들이 차례대로 "내가 처리할 수 있는 토큰인가?"를 확인하고, 가능한 Provider가 검증을 수행합니다.

        요약: Builder → Provider들 등록 → AuthenticationManager 완성.
     */

    // 여기서 쓰인 AuthenticationManagerBuiler는 AuthenticationManager를 "조립하는 빌더"
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    /*
        auth.userDetailsService(userDetailsService)
        DB에서 유저를 꺼내 검증하는 표준 Provider(=DaoAuthenticationProvider)를 등록해줘 뜻
     */

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();   // 비밀번호를 BCrypt 해시로 저장/검증
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(withDefaults())  // 여기 추가했습니다. 근데 static 메서드 추가하는 import문도 추가됨
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(exceptionHandler));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {  // CORS 설정 빈 (프론트와 백엔드 도메인이 다를 때 필수)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));  // 어떤 origin(도메인)도 허용
        config.setAllowedMethods(Arrays.asList("*"));  // GET/POST/PUT/DELETE 등 전부 허용
        config.setAllowedHeaders(Arrays.asList("*"));  // Authorization, Content-Type 등 전부 허용

        config.setAllowCredentials(false);   // 쿠키/자격증명 미허용
        config.applyPermitDefaultValues();   // ETag, Cache-Control 등 기본 헤더/옵션 추가

        source.registerCorsConfiguration("/**", config);   // 모든 경로에 이 CORS 정책 적용
        return source;
    }
}
