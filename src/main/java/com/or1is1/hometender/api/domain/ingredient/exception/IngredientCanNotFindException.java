package com.or1is1.hometender.api.domain.ingredient.exception;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class IngredientCanNotFindException extends RuntimeException {

	public static final IngredientCanNotFindException INGREDIENT_CAN_NOT_FIND_EXCEPTION
			= new IngredientCanNotFindException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
