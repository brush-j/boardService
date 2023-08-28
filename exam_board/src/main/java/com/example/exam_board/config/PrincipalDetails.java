package com.example.exam_board.config;

import com.example.exam_board.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails  implements UserDetails {
    private UserAccount userAccount;

    public PrincipalDetails(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(()->{return userAccount.getRole();});
        return collect;
    }

    @Override
    public String getPassword() {
        return userAccount.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userAccount.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
