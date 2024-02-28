package com.or1is1.hometender.api.domain.member.dto.request;

public record MemberLoginRequest (
		String loginId,
		String password
){
}
