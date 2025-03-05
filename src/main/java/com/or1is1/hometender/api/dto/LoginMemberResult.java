package com.or1is1.hometender.api.dto;

import com.or1is1.hometender.api.domain.member.Member;

public record LoginMemberResult(
		Long id,
		String nickname
) {
	public LoginMemberResult(Member member) {
		this(member.getId(), member.getNickname());
	}
}
