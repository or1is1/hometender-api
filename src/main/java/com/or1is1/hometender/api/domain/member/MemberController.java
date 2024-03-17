package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.domain.member.dto.*;
import com.or1is1.hometender.api.domain.member.exception.MemberAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.or1is1.hometender.api.StringConst.LOGIN_MEMBER;
import static com.or1is1.hometender.api.domain.member.exception.MemberNeedToLoginException.MEMBER_NEED_TO_LOGIN_EXCEPTION;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	public LoginMemberResult get(@SessionAttribute(value = LOGIN_MEMBER, required = false) Long memberId) {

		if (memberId == null) {
			throw MEMBER_NEED_TO_LOGIN_EXCEPTION;
		}

		return memberService.get(memberId);
	}

	@PostMapping
	public LoginMemberResponse post(@Validated @RequestBody PostMemberRequest postMemberRequest,
	                                HttpServletRequest httpServletRequest) {

		try {
			memberService.post(postMemberRequest);

			LoginMemberRequest loginMemberRequest = new LoginMemberRequest(postMemberRequest.loginId(), postMemberRequest.password());

			return login(loginMemberRequest, httpServletRequest);
		} catch (DataIntegrityViolationException ex) {
			throw new MemberAlreadyExistsException();
		}
	}

	@DeleteMapping
	public void delete(@SessionAttribute(value = LOGIN_MEMBER, required = false) Long memberId,
	                   @Validated @RequestBody DeleteMemberRequest deleteMemberRequest) {

		if (memberId == null) {
			throw MEMBER_NEED_TO_LOGIN_EXCEPTION;
		}

		memberService.delete(memberId, deleteMemberRequest);
	}

	@PostMapping("/login")
	public LoginMemberResponse login(@Validated @RequestBody LoginMemberRequest loginMemberRequest,
	                                 HttpServletRequest httpServletRequest) {

		LoginMemberResult loginMemberResult = memberService.login(loginMemberRequest);

		httpServletRequest.getSession().setAttribute(LOGIN_MEMBER, loginMemberResult.id());

		return new LoginMemberResponse(loginMemberResult);
	}

	@PostMapping("/logout")
	public void logout(HttpServletRequest httpServletRequest) {

		httpServletRequest.getSession().invalidate();
	}
}
