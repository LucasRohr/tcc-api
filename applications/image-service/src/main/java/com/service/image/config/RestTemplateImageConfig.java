package com.service.image.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateImageConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateImage() {
        return new RestTemplate();
    }
}