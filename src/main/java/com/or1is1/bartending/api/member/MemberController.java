package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.CommonResponse;
import com.or1is1.bartending.api.member.dto.*;
import com.or1is1.bartending.api.member.exception.MemberAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.or1is1.bartending.api.SessionConst.LOGIN_MEMBER;
import static java.util.Locale.KOREAN;

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

	/**
	 * @return 해당 회원정보를 가진 멤버가 존재한다면 lgoinId과 nickname 에 대해 각각의 중복 정보(true or false) 를 반환한다.
	 * 해당 회원정보를 가진 멤버가 없다면 false 를 반환하지 않고 null을 반환한다.
	 */
	@GetMapping("/exists")
	public CommonResponse<MemberIsExistsResult> exists(@Validated @RequestBody MemberExistsRequest memberExistsRequest) {
		return new CommonResponse<>(null, memberService.isExists(memberExistsRequest));
	}

	@PostMapping("/login")
	public CommonResponse<MemberLoginResult> login(
			@Validated @RequestBody MemberLoginRequest memberLoginRequest,
			HttpServletRequest httpServletRequest
	) {
		MemberLoginResult memberLoginResult = memberService.login(memberLoginRequest);

		httpServletRequest.getSession().setAttribute(LOGIN_MEMBER, memberLoginResult);

		return new CommonResponse<>(null, memberLoginResult);
	}
}
