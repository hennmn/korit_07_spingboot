package com.example.shoppinglist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private final String username;

    @Column(nullable = false)
    @JsonIgnore   // 비밀번호가 API 응답에 노출되지 않도록 했습니다. 어차피 암호화할거니까요.
    private final String password;

    @Column(nullable = false)
    private final String role;



    @JsonIgnoreProperties
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Shopping> shoppings;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<ShoppingItem> items;

    // CommandLineRunner사에서 사용할 간단할 생성자 정의 하겠습니다.



}
