package com.or1is1.hometender.api.domain.member.dto;

public record LoginMemberRequest(
		String loginId,
		String password
){
}
