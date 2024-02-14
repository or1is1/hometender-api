package com.or1is1.bartending.api.member.dto;

public record MemberLoginRequest (
		String loginId,
		String password
){
}
