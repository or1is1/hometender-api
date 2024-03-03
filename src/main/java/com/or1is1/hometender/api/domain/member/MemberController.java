package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.domain.member.dto.MemberLoginResult;
import com.or1is1.hometender.api.domain.member.dto.MemberExistsRequest;
import com.or1is1.hometender.api.domain.member.dto.MemberLoginRequest;
import com.or1is1.hometender.api.domain.member.dto.MemberIsExistsResponse;
import com.or1is1.hometender.api.domain.member.dto.MemberJoinRequest;
import com.or1is1.hometender.api.domain.member.dto.MemberWithdrawRequest;
import com.or1is1.hometender.api.domain.member.dto.MemberLoginResponse;
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
	public CommonResponse<MemberLoginResponse> join(@Validated @RequestBody MemberJoinRequest memberJoinRequest,
	                                                HttpServletRequest httpServletRequest) {
		try {
			memberService.join(memberJoinRequest);

			MemberLoginRequest memberLoginRequest = new MemberLoginRequest(memberJoinRequest.loginId(), memberJoinRequest.password());

			return login(memberLoginRequest, httpServletRequest);
		} catch (DataIntegrityViolationException ex) {
			throw new MemberAlreadyExistsException(memberJoinRequest);
		}
	}

	/**
	 * @return 해당 회원정보를 가진 멤버가 존재한다면 lgoinId과 nickname 에 대해 각각의 중복 정보(true or false) 를 반환한다.
	 * 해당 회원정보를 가진 멤버가 없다면 false 를 반환하지 않고 null을 반환한다.
	 */
	@GetMapping("/exists")
	public CommonResponse<MemberIsExistsResponse> exists(@Validated @RequestBody MemberExistsRequest memberExistsRequest) {
		return new CommonResponse<>(null, memberService.isExists(memberExistsRequest));
	}

	@PostMapping("/login")
	public CommonResponse<MemberLoginResponse> login(
			@Validated @RequestBody MemberLoginRequest memberLoginRequest,
			HttpServletRequest httpServletRequest
	) {
		MemberLoginResult memberLoginResult = memberService.login(memberLoginRequest);

		httpServletRequest.getSession().setAttribute(LOGIN_MEMBER, memberLoginResult.id());

		MemberLoginResponse memberLoginResponse = new MemberLoginResponse(memberLoginResult);

		return new CommonResponse<>(null, memberLoginResponse);
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
	                                     @Validated @RequestBody MemberWithdrawRequest memberWithdrawRequest) {

		memberService.withdraw(loginId, memberWithdrawRequest);

		return new CommonResponse<>("", null);
	}
}
