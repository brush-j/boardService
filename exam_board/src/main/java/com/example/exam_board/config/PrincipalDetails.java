package com.example.exam_board.config;

import com.example.exam_board.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails  implements UserDetails, OAuth2User {
    private UserAccount userAccount;

    private Map<String, Object> attributes;

    public PrincipalDetails(UserAccount user, Map<String, Object> attributes) {
        this.userAccount = user;
        this.attributes = attributes;
    }

    public PrincipalDetails(UserAccount user) {
        this.userAccount = user;
    }

    public UserAccount getUser() {
        return userAccount;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(() -> {
            return userAccount.getRole();
        });
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

    @Override
    public String getName() {
        String sub = attributes.get("id").toString();
        return sub;
    }

}