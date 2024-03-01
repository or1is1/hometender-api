package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
import com.or1is1.hometender.api.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.Locale.KOREAN;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "com.or1is1.hometender.api.domain.ingredient")
@RequiredArgsConstructor(access = PROTECTED)
public class IngredientExceptionHandler {
	private final MemberService memberService;
	private final MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public CommonResponse<Void> handleIngredientCanNotFindException(IngredientCanNotFindException ex) {
		String message = messageSource.getMessage("ingredient.exception.canNotFound", null, KOREAN);
		return new CommonResponse<>(message, null);
	}
}
