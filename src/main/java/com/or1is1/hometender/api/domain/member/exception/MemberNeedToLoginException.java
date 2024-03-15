package com.or1is1.hometender.api.domain.member.exception;

public class MemberNeedToLoginException extends RuntimeException {

	public static final MemberNeedToLoginException MEMBER_NEED_TO_LOGIN_EXCEPTION = new MemberNeedToLoginException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
