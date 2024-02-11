package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.CommonResponse;
import com.or1is1.bartending.api.member.dto.MemberJoinRequest;
import com.or1is1.bartending.api.member.dto.MemberJoinResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {
	private final MemberService memberService;

	@PostMapping
	public CommonResponse<MemberJoinResult> join(@Validated @RequestBody MemberJoinRequest memberJoinRequest) {
		return new CommonResponse<>(null, memberService.join(memberJoinRequest));
	}
}
