package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.CommonResponse;
import com.or1is1.bartending.api.member.dto.MemberIsExistsResult;
import com.or1is1.bartending.api.member.exception.MemberAlreadyExistsException;
import com.or1is1.bartending.api.member.exception.MemberCanNotFindException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.Locale.KOREAN;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "com.or1is1.bartending.api.member")
@RequiredArgsConstructor(access = PROTECTED)
public class MemberExceptionHandler {
	private final MemberService memberService;
	private final MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public CommonResponse<MemberIsExistsResult> memberAlreadyExistsException(MemberAlreadyExistsException ex) {
		String message = messageSource.getMessage("member.exception.alreadyExists", null, KOREAN);
		return new CommonResponse<>(message, memberService.isExists(ex.getMemberExistsRequest()));
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public CommonResponse<MemberIsExistsResult> memberCanNotFindException(MemberCanNotFindException ex) {
		String message = messageSource.getMessage("member.exception.canNotFound", null, KOREAN);
		return new CommonResponse<>(message, null);
	}
}
