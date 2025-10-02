package com.example.cardatabase.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

// 이걸 붙이니깐 postman에서 비밀번호 보이던 부분 404에러 뜸
@RepositoryRestResource(exported = false)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username); // Username 소문자에 주의 필드에 username 다 소문자로 적어놨기 때문에 Username이 되어야 함
    // CarRepository 처럼 List 자료형으로 선언안하는 이유는 id = unique 설정 했기 때문에
    // List로 불러올 필요가 없음

    // 이 Optional을 넣으면 이거 CRUD가 생기나,,?
}
