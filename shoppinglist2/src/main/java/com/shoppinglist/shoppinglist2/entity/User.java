package com.shoppinglist.shoppinglist2.entity;

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
public class User {
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

    // mappedBy = "user"  여기의 user은 실제 필드 이름
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<ShoppingItem> items;
}
