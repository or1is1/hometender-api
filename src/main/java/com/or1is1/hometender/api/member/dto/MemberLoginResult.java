package com.or1is1.hometender.api.member.dto;

import com.or1is1.hometender.api.member.Member;

public record MemberLoginResult(
		Long id,
		String nickname
) {
	public MemberLoginResult(Member member) {
		this(member.getId(), member.getNickname());
	}
}
