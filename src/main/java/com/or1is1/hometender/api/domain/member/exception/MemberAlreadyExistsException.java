package com.or1is1.hometender.api.domain.member.exception;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MemberAlreadyExistsException extends RuntimeException {

	public static final MemberAlreadyExistsException MEMBER_ALREADY_EXISTS_EXCEPTION
			= new MemberAlreadyExistsException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
