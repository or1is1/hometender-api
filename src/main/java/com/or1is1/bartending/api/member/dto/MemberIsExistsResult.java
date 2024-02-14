package com.or1is1.bartending.api.member.dto;

import com.querydsl.core.annotations.QueryProjection;

public record MemberIsExistsResult(
		boolean loginId,
		boolean nickname
) {
	@QueryProjection
	public MemberIsExistsResult {
	}
}
