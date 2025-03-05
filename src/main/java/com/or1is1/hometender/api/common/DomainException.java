package com.or1is1.hometender.api.common;

import lombok.Getter;

import static com.or1is1.hometender.api.common.ErrorCode.*;

@Getter
public class DomainException extends RuntimeException {
	public static final DomainException MEMBER_NEED_TO_LOGIN_EXCEPTION = new DomainException(MEMBER_NEED_TO_LOGIN);
	public static final DomainException MEMBER_ALREADY_EXISTS_EXCEPTION = new DomainException(MEMBER_ALREADY_EXISTS);
	public static final DomainException MEMBER_CAN_NOT_FIND_EXCEPTION = new DomainException(MEMBER_CAN_NOT_FIND);

	public static final DomainException INGREDIENT_CAN_NOT_FIND_EXCEPTION = new DomainException(INGREDIENT_CAN_NOT_FIND);
	public static final DomainException INGREDIENT_IS_NOT_MINE_EXCEPTION = new DomainException(INGREDIENT_IS_NOT_MINE);

	public static final DomainException RECIPE_CAN_NOT_FIND_EXCEPTION = new DomainException(RECIPE_CAN_NOT_FIND);
	public static final DomainException RECIPE_IS_NOT_MINE_EXCEPTION = new DomainException(RECIPE_IS_NOT_MINE);
	public static final DomainException RECIPE_INGREDIENT_IS_EMPTY_EXCEPTION = new DomainException(RECIPE_INGREDIENT_IS_EMPTY);

	private final ErrorCode code;

	public DomainException(ErrorCode code) {
		this.code = code;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
