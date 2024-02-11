package com.or1is1.bartending.api.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public GroupedOpenApi groupedOpenApi() {
		return GroupedOpenApi.builder()
				.group("bartending-api") // API 그룹명
				.pathsToMatch("/api/**") // API 패스 패턴
				.build();
	}
}
