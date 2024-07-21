package com.chord.lsr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import javax.servlet.Filter;

//@Configuration
public class CorsConfiguration {

    @Bean
    public Filter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // 允许的前端地址
        config.addAllowedHeader("*"); // 允许的头
        config.addAllowedMethod("*"); // 允许的方法
        source.registerCorsConfiguration("/**", config); // 应用于所有路径
        return new CorsFilter(source);
    }
}