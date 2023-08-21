package com.szzzing.api.security;

import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.security.filter.UserAuthenticationFilter;
import com.szzzing.api.security.filter.UserAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

// Spring Security 설정파일
// WebSecurityConfigurerAdapter is deprecated
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CorsConfig corsConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .addFilter(corsConfig.corsFilter());

        http
            .addFilter(new UserAuthenticationFilter(authenticationManager(), userRepository))
            .addFilter(new UserAuthorizationFilter(authenticationManager(), userRepository));

        // 페이지별 권한 설정
        http.authorizeHttpRequests()
            // api 요청 관련
            .requestMatchers("/admin/**").anonymous()
            .requestMatchers("/movie/**").hasRole("U")
            // 라우터 관련
            .requestMatchers("/route/auth/**", "/route/login").anonymous()
            .requestMatchers("/route/list", "/route/detail").permitAll()
            .requestMatchers("/route/mypage/**").authenticated()
            .anyRequest().permitAll();

        // 권한 관련 상태코드 설정
        http.exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));    // 미인증 사용자 접근시 401 반환

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable();

        return http.build();
    }

    // configure(WebSecurity web) is deprecated
    // 시큐리티 필터를 무시하는 경로 지정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**", "/upload/**", "*.ico");    // 정적 저장소에 접근하면 시큐리티 설정을 무시하도록 함
    }
}
