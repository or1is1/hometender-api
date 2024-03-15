package com.or1is1.hometender.api.domain.member.exception;

public class MemberCanNotFindException extends RuntimeException {

	public static final MemberCanNotFindException MEMBER_CAN_NOT_FIND_EXCEPTION = new MemberCanNotFindException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
