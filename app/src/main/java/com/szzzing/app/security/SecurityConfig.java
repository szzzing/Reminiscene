package com.szzzing.app.security;

import com.szzzing.app.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// WebSecurityConfigurerAdapter is deprecated
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeHttpRequests()
            .requestMatchers("/", "/login", "/join", "/selectAll").permitAll()
            .requestMatchers("/admin/**").hasRole("A")
            .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable();

        // form login 해제로 인해 직접 필터 클래스 생성
//        http.addFilter(new JwtAuthenticationFilter(authenticationManager()));
        
        return http.build();
    }

    // configure(WebSecurity web) is deprecated
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**");    // 정적 저장소에 접근하면 시큐리티 설정을 무시하도록 함
    }

}
