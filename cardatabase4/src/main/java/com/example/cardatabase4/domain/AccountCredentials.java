package com.example.cardatabase4.domain;

public record AccountCredentials(String username, String password) {
    // 얘 특징이 Getter / Setter 알아서 만들어준다
}
