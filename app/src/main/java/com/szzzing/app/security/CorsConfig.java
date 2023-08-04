package com.szzzing.app.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

// cors 공격 관련 필터
@Configuration
public class CorsConfig {
    @Value("${front.url}") String url;
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(url);    // 허용할 출처
        configuration.addAllowedHeader("*");    // 허용할 헤더
        configuration.addAllowedMethod("*");    // 허용할 메서드
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        source.registerCorsConfiguration("/login", configuration);

        return new CorsFilter(source);
    }

}
