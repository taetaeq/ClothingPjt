package com.clothing.match.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 파일 업로드 경로를 매핑
    	registry.addResourceHandler("/uploads/**") // /match/uploads/** 경로로 수정
                .addResourceLocations("file:C:/clothing/uploads/");
        System.out.println("Uploads folder mapped to /match/uploads/**");
        
    }

}
