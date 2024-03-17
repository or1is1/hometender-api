package com.or1is1.hometender.api.domain.member.exception;

public class MemberAlreadyExistsException extends RuntimeException {

	public static final MemberAlreadyExistsException MEMBER_ALREADY_EXISTS_EXCEPTION = new MemberAlreadyExistsException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
