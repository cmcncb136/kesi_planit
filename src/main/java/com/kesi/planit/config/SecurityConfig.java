package com.kesi.planit.config;

import com.kesi.planit.token.FirebaseTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final List<String> excludedUrls = List.of("/join/*", "/test/*", "/", "/h2-console/**");
    private final FirebaseTokenFilter firebaseTokenFilter =
            new FirebaseTokenFilter(excludedUrls, antPathMatcher);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(auth->auth.disable())
                .formLogin(auth->auth.disable())
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll()) //Spring Security 보안 기능보다는 필터 기능만을 쓰기 때문에 전체 허용해준다.
                .addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class); //이 필터에서 모두 차단
        return http.build();
    }
}
