package com.or1is1.hometender.api.dto;

import com.querydsl.core.annotations.QueryProjection;

public record IsExistMemberResponse(
		boolean loginId,
		boolean nickname
) {
	@QueryProjection
	public IsExistMemberResponse {
	}
}
