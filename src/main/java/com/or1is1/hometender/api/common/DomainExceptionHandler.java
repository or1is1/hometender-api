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
			case MEMBER_NEED_TO_LOGIN -> messageSource.getMessage("member.exception.needToLogin", null, KOREAN);
			case MEMBER_ALREADY_EXISTS -> messageSource.getMessage("member.exception.alreadyExists", null, KOREAN);
			case MEMBER_CAN_NOT_FIND -> messageSource.getMessage("member.exception.canNotFound", null, KOREAN);

			case INGREDIENT_CAN_NOT_FIND -> messageSource.getMessage("ingredient.exception.canNotFound", null, KOREAN);
			case INGREDIENT_IS_NOT_MINE -> messageSource.getMessage("ingredient.exception.isNotMine", null, KOREAN);

			case RECIPE_CAN_NOT_FIND -> messageSource.getMessage("recipe.exception.cantNotFind", null, KOREAN);
			case RECIPE_IS_NOT_MINE -> messageSource.getMessage("recipe.exception.isNotMine", null, KOREAN);
			case RECIPE_INGREDIENT_IS_EMPTY -> messageSource.getMessage("recipe.exception.ingredientIsEmpty", null, KOREAN);
		};

		return new ErrorResponse(code, message);
	}
}
