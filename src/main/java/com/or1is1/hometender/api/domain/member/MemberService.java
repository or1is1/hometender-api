package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.or1is1.hometender.api.common.DomainException.MEMBER_CAN_NOT_FIND_EXCEPTION;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public LoginMemberResult get(Long memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> MEMBER_CAN_NOT_FIND_EXCEPTION);

		return new LoginMemberResult(member);
	}

	@Transactional
	public void post(PostMemberRequest userSignUpRequest) {
		String loginId = userSignUpRequest.loginId();
		String password = passwordEncoder.encode(userSignUpRequest.password());
		String nickname = userSignUpRequest.nickname();

		memberRepository.save(new Member(loginId, password, nickname));
	}

	public IsExistMemberResponse isExists(IsExistMemberRequest isExistMemberRequest) {
		return memberRepository.isExists(isExistMemberRequest.loginId(), isExistMemberRequest.nickname());
	}

	@Transactional
	public Void delete(Long memberId, DeleteMemberRequest deleteMemberRequest) {
		String password = deleteMemberRequest.password();

		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> MEMBER_CAN_NOT_FIND_EXCEPTION);

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw MEMBER_CAN_NOT_FIND_EXCEPTION;
		}

		memberRepository.delete(member);

		return null;
	}

	public LoginMemberResult login(LoginMemberRequest loginMemberRequest) {
		String loginId = loginMemberRequest.loginId();
		String password = loginMemberRequest.password();

		Member member = memberRepository.findByLoginId(loginId)
				.orElseThrow(() -> MEMBER_CAN_NOT_FIND_EXCEPTION);

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw MEMBER_CAN_NOT_FIND_EXCEPTION;
		}

		return new LoginMemberResult(member);
	}
}
