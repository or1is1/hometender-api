package com.or1is1.hometender.api.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record MemberIsExistsResponse(
		boolean loginId,
		boolean nickname
) {
	@QueryProjection
	public MemberIsExistsResponse {
	}
}
