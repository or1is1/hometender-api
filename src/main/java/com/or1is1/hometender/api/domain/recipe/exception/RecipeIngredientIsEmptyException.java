package com.or1is1.hometender.api.domain.recipe.exception;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RecipeIngredientIsEmptyException extends RuntimeException {

	public static final RecipeIngredientIsEmptyException RECIPE_INGREDIENT_IS_EMPTY_EXCEPTION
			= new RecipeIngredientIsEmptyException();

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
