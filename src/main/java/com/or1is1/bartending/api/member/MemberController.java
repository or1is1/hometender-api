package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.member.dto.ExceptionResponse;
import com.or1is1.bartending.api.member.dto.MemberJoinRequest;
import com.or1is1.bartending.api.member.dto.MemberJoinResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class MemberController {
	private final MemberService memberService;

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {

		BindingResult bindingResult = ex.getBindingResult();
		String field = bindingResult.getFieldError().getField();
		ObjectError objectError = bindingResult.getAllErrors().get(0);
		String message = objectError.getDefaultMessage();
		String code = objectError.getCode();

		log.error(message, ex);

		return new ExceptionResponse(field, code, message);
	}

	@PostMapping
	public MemberJoinResponse join(@Validated @RequestBody MemberJoinRequest memberJoinRequest) {
		return memberService.join(memberJoinRequest);
	}
}
