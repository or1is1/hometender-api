package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.domain.member.MemberService;
import com.or1is1.hometender.api.domain.member.dto.IsExistMemberResponse;
import com.or1is1.hometender.api.domain.recipe.exception.RecipeIngredientIsEmptyException;
import com.or1is1.hometender.api.domain.recipe.exception.RecipeIsNotMineException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.Locale.KOREAN;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "com.or1is1.hometender.api.domain.recipe")
@RequiredArgsConstructor(access = PROTECTED)
public class RecipeExceptionHandler {
	private final MemberService memberService;
	private final MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public CommonResponse<IsExistMemberResponse> memberAlreadyExistsException(RecipeIsNotMineException ex) {
		String message = messageSource.getMessage("recipe.exception.isNotMine", null, KOREAN);
		return new CommonResponse<>(message, null);
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public CommonResponse<IsExistMemberResponse> recipeIngredientIsEmptyException(RecipeIngredientIsEmptyException ex) {
		String message = messageSource.getMessage("recipe.exception.ingredientIsEmpty", null, KOREAN);
		return new CommonResponse<>(message, null);
	}

}
