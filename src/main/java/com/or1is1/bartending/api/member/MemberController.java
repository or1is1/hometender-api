package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.CommonResponse;
import com.or1is1.bartending.api.member.dto.*;
import com.or1is1.bartending.api.member.exception.MemberAlreadyExistsException;
import com.or1is1.bartending.api.member.exception.MemberCanNotFindException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.util.Locale.KOREAN;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {
	private final MemberService memberService;
	private final MessageSource messageSource;

	@PostMapping
	public CommonResponse<MemberJoinResult> join(@Validated @RequestBody MemberJoinRequest memberJoinRequest) {
		try {
			return new CommonResponse<>(null, memberService.join(memberJoinRequest));
		} catch (DataIntegrityViolationException ex) {
			throw new MemberAlreadyExistsException(memberJoinRequest, messageSource.getMessage("member.alreadyExists", null, KOREAN));
		}
	}

	@GetMapping("/exists")
	public CommonResponse<MemberIsExistsResult> exists(@Validated @RequestBody MemberExistsRequest memberExistsRequest) {
		return new CommonResponse<>(null, memberService.isExists(memberExistsRequest));
	}

	@PostMapping("/login")
	public CommonResponse<MemberLoginResult> login(@Validated @RequestBody MemberLoginRequest memberLoginRequest) {
		return new CommonResponse<>(null, memberService.login(memberLoginRequest));
	}

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
