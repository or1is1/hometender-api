package com.or1is1.hometender.api.domain.member.dto.response;

import com.or1is1.hometender.api.domain.member.dto.MemberLoginResult;

public record MemberLoginResponse(
		String nickname
) {
	public MemberLoginResponse(MemberLoginResult memberLoginResult) {
		this(memberLoginResult.nickname());
	}
}
