package com.service.message.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateMessageConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateMessage() {
        return new RestTemplate();
    }
}