package com.clothing.match.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/uploads/**").permitAll()  // /uploads/** 경로는 모든 사용자에게 허용
                .anyRequest().authenticated()  // 다른 모든 경로는 인증 필요
            .and()
            .formLogin()
                .permitAll()  // 로그인 폼은 모두 허용
            .and()
            .logout()
                .permitAll();  // 로그아웃은 모두 허용
    }
}
