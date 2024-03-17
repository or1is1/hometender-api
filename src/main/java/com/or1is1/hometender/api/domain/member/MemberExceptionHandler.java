package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.ErrorResponse;
import com.or1is1.hometender.api.domain.member.exception.MemberAlreadyExistsException;
import com.or1is1.hometender.api.domain.member.exception.MemberCanNotFindException;
import com.or1is1.hometender.api.domain.member.exception.MemberNeedToLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.or1is1.hometender.api.ErrorCode.*;
import static java.util.Locale.KOREAN;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "com.or1is1.hometender.api.domain.member")
@RequiredArgsConstructor(access = PROTECTED)
public class MemberExceptionHandler {

	private final MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse memberNeedLoginException(MemberNeedToLoginException ex) {

		String message = messageSource.getMessage("member.exception.notAuthenticated", null, KOREAN);

		return new ErrorResponse(MEMBER_NEED_TO_LOGIN, message);
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse memberAlreadyExistsException(MemberAlreadyExistsException ex) {

		String message = messageSource.getMessage("member.exception.alreadyExists", null, KOREAN);

		return new ErrorResponse(MEMBER_ALREADY_EXISTS, message);
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse memberCanNotFindException(MemberCanNotFindException ex) {

		String message = messageSource.getMessage("member.exception.canNotFound", null, KOREAN);

		return new ErrorResponse(MEMBER_CAN_NOT_FIND, message);
	}
}
