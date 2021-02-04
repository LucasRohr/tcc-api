package com.service.auth.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateAuthConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateCredential() {
        return new RestTemplate();
    }
}