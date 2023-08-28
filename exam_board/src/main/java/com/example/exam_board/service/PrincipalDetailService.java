package com.example.exam_board.service;

import com.example.exam_board.config.PrincipalDetails;
import com.example.exam_board.entity.UserAccount;
import com.example.exam_board.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrincipalDetailService implements UserDetailsService {
    @Autowired
    UserAccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserAccount userAccount = accountRepository.findByUserId(userId);
        if (userAccount == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>(); //인증된 권한 리스트

        switch (userId.toUpperCase()){
            case "ADMIN": authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            case "USER": authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        }

        return new PrincipalDetails(userAccount);
    }
}
