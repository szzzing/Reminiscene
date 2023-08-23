package com.szzzing.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    private final String profilePath = "/Users/szzzing/Mine/project/Reminiscene/upload/profile/";
    private final String profilePath = "../../Reminiscene/upload/profile/";

    // 프로필 이미지 경로를 정적 자원으로 인식해 접근하도록 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/profile/**")
                .addResourceLocations("file:"+profilePath)
                .setCachePeriod(30);
    }
}