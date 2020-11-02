package com.service.invite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.service.user", "com.service.invite"})
public class InviteApplication {

	public static void main(String[] args) {
		SpringApplication.run(InviteApplication.class, args);
	}

}
