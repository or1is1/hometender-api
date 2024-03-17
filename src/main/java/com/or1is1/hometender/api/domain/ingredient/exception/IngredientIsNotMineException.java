package com.or1is1.hometender.api.domain.ingredient.exception;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class IngredientIsNotMineException extends RuntimeException {

	public static final IngredientIsNotMineException INGREDIENT_IS_NOT_MINE_EXCEPTION
			= new IngredientIsNotMineException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
