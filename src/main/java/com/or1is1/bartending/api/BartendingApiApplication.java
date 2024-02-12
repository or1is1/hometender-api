package com.or1is1.bartending.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BartendingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BartendingApiApplication.class, args);
	}

}
