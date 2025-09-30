package com.example.cardatabase4.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
// JpaRepository 덕분에 DB에 테이블이 만들어짐
}
