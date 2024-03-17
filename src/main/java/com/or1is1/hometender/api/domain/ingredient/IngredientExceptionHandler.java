package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.ErrorCode;
import com.or1is1.hometender.api.ErrorResponse;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientIsNotMineException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.or1is1.hometender.api.ErrorCode.INGREDIENT_IS_NOT_MINE;
import static java.util.Locale.KOREAN;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "com.or1is1.hometender.api.domain.ingredient")
@RequiredArgsConstructor(access = PROTECTED)
public class IngredientExceptionHandler {
	private final MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse ingredientCanNotFindException(IngredientCanNotFindException ex) {

		String message = messageSource.getMessage("ingredient.exception.canNotFound", null, KOREAN);

		return new ErrorResponse(ErrorCode.INGREDIENT_CAN_NOT_FIND, message);
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse ingredientIsNotMineException(IngredientIsNotMineException ex) {

		String message = messageSource.getMessage("ingredient.exception.isNotMine", null, KOREAN);

		return new ErrorResponse(INGREDIENT_IS_NOT_MINE, message);
	}
}
