package com.example.exam_board.config;

import com.example.exam_board.service.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    PrincipalOauth2UserService principalOauth2UserService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers("/articles/login", "/articles/list", "/articles/lists", "/articles/loginForm", "/articles/join", "/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/articles/{id}").authenticated()
                                .requestMatchers("/articles/{id}/delete").hasRole("ADMIN")
                                .anyRequest().permitAll())
                .csrf((csrf)->csrf.disable())
                .formLogin((form) ->
                        form.loginPage("/articles/loginForm")
                                .loginProcessingUrl("/articles/login")
                                .defaultSuccessUrl("/articles/lists"))
                .logout((logout) ->
                        logout
                                .logoutUrl("/articles/logout")
                                .logoutSuccessUrl("/articles/lists"))
                .oauth2Login(oAuth -> oAuth
                .loginPage("/loginForm")
                .defaultSuccessUrl("/articles/lists")
                .userInfoEndpoint(userInfo-> userInfo
                        .userService(principalOauth2UserService))
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
