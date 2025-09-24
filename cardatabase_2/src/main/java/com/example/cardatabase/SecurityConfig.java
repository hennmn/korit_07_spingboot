package com.example.cardatabase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
// 위에꺼 복사해서 Details포함
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// bean등록할 때 쓰는 거
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 프로젝트 들어갔을 때 인메모리 사용하는 사람 못봄
    // 오늘 보는 게 마지막
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // 이거는 암호화를 위한 과정입니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 이거 해놔서 콘솔창에 비밀번호가 안 뜸
    }
    // 20250924 14:40에 업로드 합니다.
}






