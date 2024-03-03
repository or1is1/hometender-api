package com.or1is1.hometender.api.domain.member.dto;

public record LoginMemberResponse(
		String nickname
) {
	public LoginMemberResponse(LoginMemberResult loginMemberResult) {
		this(loginMemberResult.nickname());
	}
}
