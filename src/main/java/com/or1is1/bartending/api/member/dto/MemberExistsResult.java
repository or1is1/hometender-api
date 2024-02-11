package com.or1is1.bartending.api.member.dto;

import com.querydsl.core.annotations.QueryProjection;

public record MemberExistsResult(
		boolean loginId,
		boolean nickname
) {
	@QueryProjection
	public MemberExistsResult {
	}
}
