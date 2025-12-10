package com.example.todolist_project.service;

import com.example.todolist_project.domain.AppUser;
import com.example.todolist_project.domain.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    public AppUserRepository appUserRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        /*
            AppUserRepository에서 가져오는 AppUser 이건 내가 만든 JPA 엔티티
            DB에 저장된 사용자의 정보(아이디, 비번, 권한 등)가 AppUser객체로 매핑돼서 나옴
            하지만 스프링 시큐리티의 인증 시스템은 AppUser를 직접
         */
        UserBuilder builder = null;    // DB 엔티티(AppUser) -> 스프링 시큐리티 전용 UserDetails 객체로 변환하는 과정
        if(user.isPresent()) {
            AppUser currentUser = user.get();
            builder = User.withUsername(username);  // 맨 위에 UserBuiler랑 AppUser currentUser랑 자료형이 달라서 이 메서드를 경유해준 것임
            // withUsername() 정적메서드는 UserBilder의 빈껍데기 객체를 만들어 주는 역할임 () 값으로 currentUser.getusername()이 아닌 이유는
            // 어차피 객체가 찾아진 것이라면 currentUser.getusername() 과 위에 가져온 username이 같기 때문에 어차피 같은 거면
            // 길이가 더 짧은 username을 쓴 거임
            builder.password(currentUser.getPassword());
            builder.roles(currentUser.getRole());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return builder.build();
    }
}
