package com.szzzing.api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// cors 공격 관련 필터
@ComponentScan(basePackages = "com.szzzing.api")
@Configuration
public class CorsConfig {
    @Value("${front.url}") static String url;

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
