package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.domain.member.dto.*;
import com.or1is1.hometender.api.domain.member.dto.IsExistMemberResponse;
import com.or1is1.hometender.api.domain.member.repository.MemberRepository;
import com.or1is1.hometender.api.domain.member.exception.MemberCanNotFindException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MessageSource messageSource;

	@Transactional
	public void join(PostMemberRequest userSignUpRequest) {
		String loginId = userSignUpRequest.loginId();
		String password = passwordEncoder.encode(userSignUpRequest.password());
		String nickname = userSignUpRequest.nickname();

		memberRepository.save(new Member(loginId, password, nickname));
	}

	public LoginMemberResult login(LoginMemberRequest loginMemberRequest) {
		String loginId = loginMemberRequest.loginId();
		String password = loginMemberRequest.password();
		MemberCanNotFindException memberCanNotFindException = new MemberCanNotFindException();

		Member member = memberRepository.findByLoginId(loginId)
				.orElseThrow(() -> memberCanNotFindException);

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw memberCanNotFindException;
		}

		return new LoginMemberResult(member);
	}

	public IsExistMemberResponse isExists(IsExistMemberRequest isExistMemberRequest) {
		return memberRepository.isExists(isExistMemberRequest.loginId(), isExistMemberRequest.nickname());
	}

	@Transactional
	public Void withdraw(String loginId, DeleteMemberRequest deleteMemberRequest) {
		String password = deleteMemberRequest.password();
		MemberCanNotFindException memberCanNotFindException = new MemberCanNotFindException();

		Member member = memberRepository.findByLoginId(loginId)
				.orElseThrow(() -> memberCanNotFindException);

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw memberCanNotFindException;
		}

		memberRepository.delete(member);

		return null;
	}
}