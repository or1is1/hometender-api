package com.or1is1.hometender.api.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.Locale.KOREAN;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "com.or1is1.hometender.api.domain")
@RequiredArgsConstructor(access = PROTECTED)
@Slf4j
public class DomainExceptionHandler {

	private final MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public List<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();

		if (bindingResult.hasErrors()) {
			log.warn(ex.getMessage(), ex);

			return bindingResult.getAllErrors()
					.stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.toList();
		}

		String message = messageSource.getMessage("exception", null, KOREAN);

		throw new RuntimeException(message, ex);
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse memberNeedLoginException(DomainException ex) {

		ErrorCode code = ex.getCode();

		String message = switch (code) {
			case MEMBER_NEED_TO_LOGIN -> messageSource.getMessage("exception.member.needToLogin", null, KOREAN);
			case MEMBER_ALREADY_EXISTS -> messageSource.getMessage("exception.member.alreadyExists", null, KOREAN);
			case MEMBER_CAN_NOT_FIND -> messageSource.getMessage("exception.member.canNotFound", null, KOREAN);

			case INGREDIENT_CAN_NOT_FIND -> messageSource.getMessage("exception.ingredient.canNotFound", null, KOREAN);
			case INGREDIENT_IS_NOT_MINE -> messageSource.getMessage("exception.ingredient.isNotMine", null, KOREAN);

			case RECIPE_CAN_NOT_FIND -> messageSource.getMessage("exception.recipe.cantNotFind", null, KOREAN);
			case RECIPE_IS_NOT_MINE -> messageSource.getMessage("exception.recipe.isNotMine", null, KOREAN);
			case RECIPE_INGREDIENT_IS_EMPTY -> messageSource.getMessage("exception.recipe.ingredientIsEmpty", null, KOREAN);
		};

		return new ErrorResponse(code, message);
	}
}
