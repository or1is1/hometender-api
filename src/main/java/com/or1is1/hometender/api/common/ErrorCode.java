package com.or1is1.hometender.api.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import static com.or1is1.hometender.api.common.ErrorCode.Domain.*;
import static com.or1is1.hometender.api.common.ErrorCode.Reason.*;

@RequiredArgsConstructor
public enum ErrorCode {
	MEMBER_NEED_TO_LOGIN(MEMBER + NEED_TO_LOGIN),
	MEMBER_ALREADY_EXISTS(MEMBER + ALREADY_EXISTS),
	MEMBER_CAN_NOT_FIND(MEMBER + CAN_NOT_FIND),

	INGREDIENT_CAN_NOT_FIND(INGREDIENT + CAN_NOT_FIND),
	INGREDIENT_IS_NOT_MINE(INGREDIENT + IS_NOT_MINE),


	RECIPE_CAN_NOT_FIND(RECIPE + CAN_NOT_FIND),
	RECIPE_IS_NOT_MINE(RECIPE + IS_NOT_MINE),

	RECIPE_INGREDIENT_IS_EMPTY(RECIPE_INGREDIENT + IS_EMPTY),

	;

	@JsonValue
	private final String code;

	protected static class Domain {
		static final String MEMBER = "000";
		static final String INGREDIENT = "001";
		static final String RECIPE = "002";
		static final String RECIPE_INGREDIENT = "003";
		static final String BOOKMARK = "004";
	}

	protected static class Reason {
		static final String NEED_TO_LOGIN = "000";
		static final String ALREADY_EXISTS = "001";
		static final String CAN_NOT_FIND = "002";
		static final String IS_NOT_MINE = "003";
		static final String IS_EMPTY = "004";
	}
}
