package com.example.todolist_project.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    // AppUser에 있는 username만 가지고 만드는 게아니라 AppUserRepository에 Optional<AppUser>가 있어야 가져올 수 있음
    // Jpa는 기본적으로 findById(),findBy() 밖에 없기 때문에 이외는 우리가 정의해줘야함
}
