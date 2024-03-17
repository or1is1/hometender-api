package com.or1is1.hometender.api.domain.member.exception;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MemberNeedToLoginException extends RuntimeException {

	public static final MemberNeedToLoginException MEMBER_NEED_TO_LOGIN_EXCEPTION
			= new MemberNeedToLoginException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
