package com.or1is1.bartending.api.member.dto;

import com.or1is1.bartending.api.member.Member;

public record MemberLoginResult(
		String id,
		String nickname
) {
	public MemberLoginResult(Member member) {
		this(member.getLoginId(), member.getNickname());
	}
}
