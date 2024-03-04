package com.or1is1.hometender.api.domain.recipe.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;


public class RecipeRepositoryImpl implements RecipeRepositoryInterface {
	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public RecipeRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}
}
