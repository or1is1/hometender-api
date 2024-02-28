package com.or1is1.hometender.api.domain.ingredient.repository;

import com.or1is1.hometender.api.domain.member.dto.response.MemberIsExistsResponse;
import com.or1is1.hometender.api.domain.member.dto.response.QMemberIsExistsResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import static com.or1is1.hometender.api.domain.member.QMember.member;


public class IngredientRepositoryImpl implements IngredientRepositoryInterface {
	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public IngredientRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}
}
