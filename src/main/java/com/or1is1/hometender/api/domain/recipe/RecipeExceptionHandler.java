package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.ErrorResponse;
import com.or1is1.hometender.api.domain.recipe.exception.RecipeCanNotFindException;
import com.or1is1.hometender.api.domain.recipe.exception.RecipeIngredientIsEmptyException;
import com.or1is1.hometender.api.domain.recipe.exception.RecipeIsNotMineException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.or1is1.hometender.api.ErrorCode.*;
import static java.util.Locale.KOREAN;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "com.or1is1.hometender.api.domain.recipe")
@RequiredArgsConstructor(access = PROTECTED)
public class RecipeExceptionHandler {

	private final MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse recipeCanNotFindException(RecipeCanNotFindException ex) {

		String message = messageSource.getMessage("recipe.exception.cantNotFind", null, KOREAN);
		
		return new ErrorResponse(RECIPE_CAN_NOT_FIND, message);
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse recipeIsNotMineException(RecipeIsNotMineException ex) {

		String message = messageSource.getMessage("recipe.exception.isNotMine", null, KOREAN);

		return new ErrorResponse(RECIPE_IS_NOT_MINE, message);
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse recipeIngredientIsEmptyException(RecipeIngredientIsEmptyException ex) {

		String message = messageSource.getMessage("recipe.exception.ingredientIsEmpty", null, KOREAN);

		return new ErrorResponse(RECIPE_INGREDIENT_IS_EMPTY, message);
	}

}
