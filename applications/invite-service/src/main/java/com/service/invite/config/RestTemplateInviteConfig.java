package com.service.invite.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateInviteConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateInvite() {
        return new RestTemplate();
    }
}