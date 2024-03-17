package com.or1is1.hometender.api.domain.recipe.exception;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RecipeIsNotMineException extends RuntimeException {

	public static final RecipeIsNotMineException RECIPE_IS_NOT_MINE_EXCEPTION
			= new RecipeIsNotMineException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
