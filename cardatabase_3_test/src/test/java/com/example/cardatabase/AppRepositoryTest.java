package com.example.cardatabase;

import com.example.cardatabase.domain.AppUser;
import com.example.cardatabase.domain.AppUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@DataJpaTest
public class AppRepositoryTest {
    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @DisplayName("유저 데이터 조회 메서드 테스트")
    void findByUsernameShouldReturnUsername() {
        // 제반 상황
        appUserRepository.save(new AppUser("user1","1234","USER"));

        // when - 테스트 상황
        Optional<AppUser> foundUser = appUserRepository.findByUsername("user1");


        // then - 결과
        assertThat(foundUser).isPresent();   // Optional 이니깐 있을 수도 있고 없을 수도 있으니까.
        assertThat(foundUser.get().getRole()).isEqualTo("USER");

    }

}
