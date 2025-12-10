package com.example.cardatabase4.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor(force = true)// JPA 때문에 기본 생성자 필요함
@RequiredArgsConstructor
@Getter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false, updatable = false)
    private Long id;   // DB에서의 고유값 밑에 username이랑 구분하기


    @Column(nullable = false, unique = true)  // 아이디는 중복 x 그래서 unique
    // 로그인 아이디 같은 거
    private final String username;

    @Column(nullable = false)
    private final String password;

    @Column(nullable = false)
    private final String role;
    // Owner 에는 nonnull

}
