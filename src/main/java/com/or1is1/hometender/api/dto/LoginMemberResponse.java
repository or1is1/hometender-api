package com.or1is1.hometender.api.dto;

public record LoginMemberResponse(
		String nickname
) {
	public LoginMemberResponse(LoginMemberResult loginMemberResult) {
		this(loginMemberResult.nickname());
	}
}
