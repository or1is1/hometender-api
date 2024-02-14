package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.CommonResponse;
import com.or1is1.bartending.api.member.dto.MemberIsExistsResult;
import com.or1is1.bartending.api.member.exception.MemberAlreadyExistsException;
import com.or1is1.bartending.api.member.exception.MemberCanNotFindException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "com.or1is1.bartending.api.member")
@RequiredArgsConstructor(access = PROTECTED)
public class MemberExceptionHandler {
	private final MemberService memberService;

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public CommonResponse<MemberIsExistsResult> memberAlreadyExistsException(MemberAlreadyExistsException ex) {
		return new CommonResponse<>(ex.getMessage(), memberService.isExists(ex.getMemberExistsRequest()));
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public CommonResponse<MemberIsExistsResult> memberCanNotFindException(MemberCanNotFindException ex) {
		return new CommonResponse<>(ex.getMessage(), null);
	}
}
