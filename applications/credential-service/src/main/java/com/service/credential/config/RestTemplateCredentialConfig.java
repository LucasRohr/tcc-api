package com.service.credential.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateCredentialConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateCredential() {
        return new RestTemplate();
    }
}