package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.dto.QIsExistMemberResponse;
import com.or1is1.hometender.api.dto.IsExistMemberResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.or1is1.hometender.api.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryInterface {
	private final JPAQueryFactory jpaQueryFactory;

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
