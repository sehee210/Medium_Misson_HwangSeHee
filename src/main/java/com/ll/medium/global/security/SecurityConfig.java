package com.ll.medium.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration          //스프링의 환경설정 파일임을 의미(스프링 시큐리티 설정을 위해 사용)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {        //로그인을 하지 않아도 모든 페이지에 접근할 수 있도록 함
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers("/**")
                                .permitAll()
                )
                .headers(
                        headers ->
                                headers.frameOptions(
                                        frameOptions ->
                                                frameOptions.sameOrigin()
                                )
                )
                .csrf(              //csrf 처리시 H2콘솔 예외처리
                        csrf ->
                                csrf.ignoringRequestMatchers(
                                        "/h2-console/**"
                                )
                );

        return http.build();
    }
}
