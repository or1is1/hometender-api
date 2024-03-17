package com.or1is1.hometender.api;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import static com.or1is1.hometender.api.ErrorCode.Domain.*;

@RequiredArgsConstructor
public enum ErrorCode {
	MEMBER_ALREADY_EXISTS(MEMBER + "001"),
	MEMBER_CAN_NOT_FIND(MEMBER + "002"),
	MEMBER_NEED_TO_LOGIN(MEMBER + "003"),
	MEMBER_NOT_AUTHENTICATED(MEMBER + "004"),

	INGREDIENT_CAN_NOT_FIND(INGREDIENT + "001"),
	INGREDIENT_IS_NOT_MINE(INGREDIENT + "002"),

	RECIPE_CAN_NOT_FIND(RECIPE + "001"),
	RECIPE_IS_NOT_MINE(RECIPE + "002"),
	RECIPE_INGREDIENT_IS_EMPTY(RECIPE + "003"),

	;

	@JsonValue
	private final String code;

	protected static class Domain {
		static final String MEMBER = "001";
		static final String INGREDIENT = "002";
		static final String RECIPE = "003";
		static final String RECIPE_INGREDIENT = "004";
		static final String BOOKMARK = "005";
	}
}
