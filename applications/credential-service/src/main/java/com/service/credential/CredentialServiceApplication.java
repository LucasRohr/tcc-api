package com.service.credential;

import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.service.credential", "com.service.common"})
@EnableEurekaClient
public class CredentialServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CredentialServiceApplication.class, args);
    }

}