package com.example.cardatabase4.service;

import com.example.cardatabase4.domain.AppUser;
import com.example.cardatabase4.domain.AppUserRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  //username을 통해서 User객체를 가져옴
        Optional<AppUser> user = appUserRepository.findByUsername(username);  // AppUser에 있는 username만 가지고 만드는 게아니라 AppUserRepository에 Optional<AppUser>가 있어야 가져올 수 있음  // Jpa는 기본적으로 findById(),findBy() 밖에 없기 때문에 이외는 우리가 정의해줘야함

        UserBuilder builder = null;
        if(user.isPresent()) {
            AppUser currentUser = user.get();   // user 자체는 Optional 자료형이지 AppUser가 아닙니다. 그래서 무조건 get해서 가져와야함
            builder = withUsername(username);
            builder.password(currentUser.getPassword()).roles(currentUser.getRole()).build();

        } else {
            throw new UsernameNotFoundException("User Not Found.");
        }
        return builder.build();
    }
}
