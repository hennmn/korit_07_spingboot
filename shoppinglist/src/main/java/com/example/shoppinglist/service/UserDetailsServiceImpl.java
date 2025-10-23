package com.example.shoppinglist.service;

import com.example.shoppinglist.domain.AppUser;
import com.example.shoppinglist.domain.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository userRepository;

    public UserDetailsServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByUsername(username);

        User.UserBuilder builder = null;
        if (user.isPresent()) { // 이하의 실행문이 실행된다면 user에 AppUser 객체가 있다는 의미(검색이 된다면)
            AppUser currentUser = user.get();   // 존재한다면 그 객체를 와서 집어넣겠다.
            builder = User.withUsername(username);  // UserDetails 객체를 만들기 위한 UserBuilder를 생성하는 시작점
            builder.password(currentUser.getPassword());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return builder.build();
    }
}

// withUsername() 메서드는 Spring Security의 User 클래스 안에 있는 정적(static) 팩토리 메서드입니다.
