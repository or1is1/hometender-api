package com.or1is1.hometender.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HometenderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HometenderApiApplication.class, args);
	}

}
