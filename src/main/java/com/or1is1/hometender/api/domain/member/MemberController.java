package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.domain.member.dto.*;
import com.or1is1.hometender.api.domain.member.dto.IsExistMemberResponse;
import com.or1is1.hometender.api.domain.member.exception.MemberAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.or1is1.hometender.api.StringConst.LOGIN_MEMBER;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {
	private final MemberService memberService;
	private final MessageSource messageSource;

	@PostMapping("/join")
	public CommonResponse<LoginMemberResponse> join(@Validated @RequestBody PostMemberRequest postMemberRequest,
	                                                HttpServletRequest httpServletRequest) {
		try {
			memberService.join(postMemberRequest);

			LoginMemberRequest loginMemberRequest = new LoginMemberRequest(postMemberRequest.loginId(), postMemberRequest.password());

			return login(loginMemberRequest, httpServletRequest);
		} catch (DataIntegrityViolationException ex) {
			throw new MemberAlreadyExistsException(postMemberRequest);
		}
	}

	/**
	 * @return 해당 회원정보를 가진 멤버가 존재한다면 lgoinId과 nickname 에 대해 각각의 중복 정보(true or false) 를 반환한다.
	 * 해당 회원정보를 가진 멤버가 없다면 false 를 반환하지 않고 null을 반환한다.
	 */
	@GetMapping("/exists")
	public CommonResponse<IsExistMemberResponse> exists(@Validated @RequestBody IsExistMemberRequest isExistMemberRequest) {
		return new CommonResponse<>(null, memberService.isExists(isExistMemberRequest));
	}

	@PostMapping("/login")
	public CommonResponse<LoginMemberResponse> login(
			@Validated @RequestBody LoginMemberRequest loginMemberRequest,
			HttpServletRequest httpServletRequest
	) {
		LoginMemberResult loginMemberResult = memberService.login(loginMemberRequest);

		httpServletRequest.getSession().setAttribute(LOGIN_MEMBER, loginMemberResult.id());

		LoginMemberResponse loginMemberResponse = new LoginMemberResponse(loginMemberResult);

		return new CommonResponse<>(null, loginMemberResponse);
	}

	@PostMapping("/logout")
	public CommonResponse<Boolean> logout(
			HttpServletRequest httpServletRequest
	) {
		HttpSession session = httpServletRequest.getSession(false);

		boolean hadInvalidate = session != null;
		if (hadInvalidate) {
			session.invalidate();
		}

		return new CommonResponse<>(null, hadInvalidate);
	}

	@DeleteMapping("/{loginId}")
	public CommonResponse<Void> withdraw(@PathVariable @NotBlank(message = "{validation.constraints.NotBlank}")
	                                     @Size(min = 5, max = 20, message = "{validation.constraints.Size.loginId}")
	                                     String loginId,
	                                     @Validated @RequestBody DeleteMemberRequest deleteMemberRequest) {

		memberService.withdraw(loginId, deleteMemberRequest);

		return new CommonResponse<>("", null);
	}
}
