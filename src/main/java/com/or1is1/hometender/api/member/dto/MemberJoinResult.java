package com.or1is1.hometender.api.member.dto;

import com.or1is1.hometender.api.member.Member;

public record MemberJoinResult(
		Long id,
		String nickname
) {
	public MemberJoinResult(Member member) {
		this(member.getId(), member.getNickname());
	}
}
