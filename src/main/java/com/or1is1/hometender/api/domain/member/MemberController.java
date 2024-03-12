package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.domain.member.dto.*;
import com.or1is1.hometender.api.domain.member.exception.MemberAlreadyExistsException;
import com.or1is1.hometender.api.domain.member.exception.MemberNeedToLoginException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.or1is1.hometender.api.StringConst.LOGIN_MEMBER;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	public CommonResponse<LoginMemberResult> get(@SessionAttribute(value = LOGIN_MEMBER, required = false) Long memberId) {

		LoginMemberResult loginMemberResult = memberService.get(memberId);

		return new CommonResponse<>("", loginMemberResult);
	}

	@PostMapping
	public CommonResponse<LoginMemberResponse> post(@Validated @RequestBody PostMemberRequest postMemberRequest,
	                                                HttpServletRequest httpServletRequest) {

		try {
			memberService.post(postMemberRequest);

			LoginMemberRequest loginMemberRequest = new LoginMemberRequest(postMemberRequest.loginId(), postMemberRequest.password());

			return login(loginMemberRequest, httpServletRequest);
		} catch (DataIntegrityViolationException ex) {
			throw new MemberAlreadyExistsException(postMemberRequest);
		}
	}

	@DeleteMapping
	public CommonResponse<Void> delete(@SessionAttribute(value = LOGIN_MEMBER, required = false) Long memberId,
	                                   @Validated @RequestBody DeleteMemberRequest deleteMemberRequest) {

		if (memberId == null) {
			throw new MemberNeedToLoginException();
		}

		memberService.delete(memberId, deleteMemberRequest);

		return new CommonResponse<>("", null);
	}

	@PostMapping("/login")
	public CommonResponse<LoginMemberResponse> login(@Validated @RequestBody LoginMemberRequest loginMemberRequest,
	                                                 HttpServletRequest httpServletRequest) {

		LoginMemberResult loginMemberResult = memberService.login(loginMemberRequest);

		httpServletRequest.getSession().setAttribute(LOGIN_MEMBER, loginMemberResult.id());

		LoginMemberResponse loginMemberResponse = new LoginMemberResponse(loginMemberResult);

		return new CommonResponse<>(null, loginMemberResponse);
	}

	@PostMapping("/logout")
	public CommonResponse<Void> logout(HttpServletRequest httpServletRequest) {

		httpServletRequest.getSession().invalidate();

		return new CommonResponse<>(null, null);
	}
}
