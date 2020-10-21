package com.service.user.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateUserConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateUser() {
        return new RestTemplate();
    }
}