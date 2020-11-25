package com.service.document.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateDocumentConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateDocument() {
        return new RestTemplate();
    }
}