package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.dto.IsExistMemberResponse;
import com.or1is1.hometender.api.domain.member.dto.QIsExistMemberResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import static com.or1is1.hometender.api.domain.member.QMember.member;


public class MemberRepositoryImpl implements MemberRepositoryInterface {
	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public MemberRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public IsExistMemberResponse isExists(String loginId, String nickname) {
		return jpaQueryFactory.select(new QIsExistMemberResponse(
						member.loginId.eq(loginId),
						member.nickname.eq(nickname)))
				.from(member)
				.where(member.loginId.eq(loginId)
						.or(member.nickname.eq(nickname)))
				.fetchOne();
	}
}
