package com.yale.fullstackbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authorize) -> {
//            // 這段表示驗證每個請求
//            authorize
//                    .anyRequest()
//                    .permitAll();
////                    .authenticated();
//        });
//        return http.build();
//    }
}
