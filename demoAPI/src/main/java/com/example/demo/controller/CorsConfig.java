package com.example.demo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // すべてのパスに対して
                .allowedOrigins(DemoRestController.ORIGINAL_URL) // 許可するオリジン (例: http://example.com)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 許可するHTTPメソッド
                .allowedHeaders("*") // 許可するヘッダー
                .allowCredentials(true) // 必要に応じて
                .maxAge(3600); // プリフライトリクエストのキャッシュ時間 (秒)
    }
    
}