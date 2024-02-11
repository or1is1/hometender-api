package com.or1is1.bartending.api.member.repository;

import com.or1is1.bartending.api.member.dto.MemberIsExistsResult;
import com.or1is1.bartending.api.member.dto.QMemberIsExistsResult;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.or1is1.bartending.api.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryInterface {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public MemberIsExistsResult isExists(String loginId, String nickname) {
		return jpaQueryFactory.select(new QMemberIsExistsResult(
						member.loginId.eq(loginId),
						member.nickname.eq(nickname)))
				.from(member)
				.where(member.loginId.eq(loginId)
						.or(member.nickname.eq(nickname)))
				.fetchOne();
	}
}
