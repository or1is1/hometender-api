package com.or1is1.hometender.api.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

	@Bean
	public JPAQueryFactory queryDslBean(EntityManager entityManager) {
		return new JPAQueryFactory(entityManager);
	}

}
