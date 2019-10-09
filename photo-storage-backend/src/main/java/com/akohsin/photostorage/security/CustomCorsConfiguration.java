package com.akohsin.photostorage.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

/**
 * AKN 7/1/19
 */

@Configuration
public class CustomCorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("*/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
//                .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Access-Control-Allow-Origin", "Origin", "Access-Control-Request-Method",
//                        "Access-Control-Request-Headers")
//                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", SecurityConstants.HEADER_STRING)
//                .allowCredentials(true).maxAge(3600);
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
                .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Access-Control-Allow-Origin", "Origin", "Access-Control-Request-Method",
                        "Access-Control-Request-Headers")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", SecurityConstants.HEADER_STRING)
                .allowCredentials(true).maxAge(3600);
    }

}