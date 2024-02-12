package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.CommonResponse;
import com.or1is1.bartending.api.member.dto.MemberExistsRequest;
import com.or1is1.bartending.api.member.dto.MemberExistsResult;
import com.or1is1.bartending.api.member.dto.MemberJoinRequest;
import com.or1is1.bartending.api.member.dto.MemberJoinResult;
import com.or1is1.bartending.api.member.exception.MemberExistsException;
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
			throw new MemberExistsException(memberJoinRequest, messageSource.getMessage("member.duplicated", null, KOREAN));
		}
	}

	@GetMapping("/exists")
	public CommonResponse<MemberExistsResult> exists(@Validated @RequestBody MemberExistsRequest memberExistsRequest) {
		return new CommonResponse<>(null, memberService.isExists(memberExistsRequest));
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public CommonResponse<MemberExistsResult> memberExistsExceptionHandler(MemberExistsException ex) {
		return new CommonResponse<>(ex.getMessage(), memberService.isExists(ex.getMemberExistsRequest()));
	}
}
