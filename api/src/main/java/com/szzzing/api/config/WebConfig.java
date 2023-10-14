package com.szzzing.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 정적 자원 설정
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/upload/profile/**")
                .addResourceLocations("file:../upload/profile/")
                .setCachePeriod(30);

        registry.addResourceHandler("/upload/backdrop/**")
                .addResourceLocations("file:../upload/backdrop/")
                .setCachePeriod(30);

        registry.addResourceHandler("/upload/poster/**")
                .addResourceLocations("file:../upload/poster/")
                .setCachePeriod(30);

    }
}