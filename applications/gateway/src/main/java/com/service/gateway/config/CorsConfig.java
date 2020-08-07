//package com.service.gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**")
////                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
////                .allowedOrigins("*");
////    }
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        System.out.println("chegando");
//        return new WebMvcConfigurer() {
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowedMethods("GET", "POST", "OPTIONS")
//                        .allowedHeaders("Authorization");
//            }
//        };
//    }
//
//}