package com.example.cardatabase;

import com.example.cardatabase.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
// 위에꺼 복사해서 Details포함
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

// bean등록할 때 쓰는 거
@Configuration
@EnableWebSecurity   // 보안 설정 커스터마이징
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;
//    private final CorsConfigurationSource corsConfigurationSource;   // @Bean 주입해서 굳이 필요없다함


    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthenticationFilter authenticationFilter, AuthEntryPoint exceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
        this.exceptionHandler = exceptionHandler;
    }

    // 이 클래스 빈 등록 안 한 이유는 어차피 전역에서 굴러가는 거라서 굳이 bean 등록이 필요없음
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    // 이거는 암호화를 위한 과정입니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 이거 해놔서 콘솔창에 비밀번호가 안 뜸
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {   // 모든 요청에 대해서 허용하는 것으로 filterchain 변경


        // 개발 중 로그인 포함 모든 HTTP 메서드 요청 허용
//        http.csrf(csrf -> csrf.disable())
//                .cors(withDefaults())
//                .authorizeHttpRequests(authorizeHttpRequests ->
//                        authorizeHttpRequests.anyRequest().permitAll());

        // 로그인 엔드포인트 POST 요청 제외 나머지 인증 필요
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

//    @Bean
//    public CorsConfigurationSource configurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Arrays.asList("*"));  // 모든 http를 허용 한다는 뜻
//        config.setAllowedMethods(Arrays.asList("*"));
//        config.setAllowedHeaders(Arrays.asList("*"));
//
//        config.setAllowCredentials(false);
//        config.applyPermitDefaultValues();
//
//        source.registerCorsConfiguration("/**", config);  // 모든 하위 폴더를 포함
//        return source;
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:5174")); // 배열 형태라 하나 정도 더 넣어도 노상관
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}






