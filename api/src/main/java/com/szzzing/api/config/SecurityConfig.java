package com.szzzing.api.config;

import com.szzzing.api.dto.user.TokenRedisDto;
import com.szzzing.api.repository.TokenRepository;
import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.security.filter.UserAuthenticationFilter;
import com.szzzing.api.security.filter.UserAuthorizationFilter;
import com.szzzing.api.security.handler.RemoveRedisTokenHandler;
import com.szzzing.api.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
@Slf4j
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtProperties jwtProperties;
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

        http.csrf(csrf -> csrf
            .disable()
            .addFilter(corsConfig.corsFilter()));

        http
            .addFilter(new UserAuthenticationFilter(authenticationManager(), userRepository, tokenRepository))
            .addFilter(new UserAuthorizationFilter(authenticationManager(), userRepository, tokenRepository));

        // 페이지별 권한 설정
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            // 라우터 관련
            .requestMatchers("/route/auth").anonymous()
            .requestMatchers("/route/mypage").hasRole("U")
            .requestMatchers("/route/admin").hasRole("A")
            .requestMatchers(HttpMethod.POST, "/rate", "/wish", "/watching", "/comment", "/reply","/like", "/report").hasRole("U")
            .requestMatchers(HttpMethod.PUT, "/rate", "/wish", "/watching", "/comment", "/reply","/like", "/report").hasRole("U")
            .requestMatchers(HttpMethod.DELETE, "/rate/**", "/wish/**", "/watching/**", "/comment/**", "/reply/**","/like/**", "/report/**").hasRole("U")
            .anyRequest().permitAll()
        );

        http.logout(logout -> logout
            .logoutUrl("/logout/{id}")
            .logoutSuccessHandler(new RemoveRedisTokenHandler(tokenRepository))
            .permitAll()
        );

        // 권한 관련 상태코드 설정
        http.exceptionHandling(exceptionHandling -> exceptionHandling
            // 미인증 사용자 접근시 401 반환
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        );

        http.sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**", "/upload/**", "*.ico");    // 정적 저장소에 접근하면 시큐리티 설정을 무시하도록 함
//    }
}
