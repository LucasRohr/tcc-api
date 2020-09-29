package com.service.invite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class InviteApplication {

	public static void main(String[] args) {
		SpringApplication.run(InviteApplication.class, args);
	}

}
