package com.or1is1.hometender.api.domain.recipe.exception;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RecipeCanNotFindException extends RuntimeException {

	public static final RecipeCanNotFindException RECIPE_CAN_NOT_FIND_EXCEPTION
			= new RecipeCanNotFindException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
