package com.or1is1.hometender.api.member;

import com.or1is1.hometender.api.member.dto.request.MemberExistsRequest;
import com.or1is1.hometender.api.member.dto.request.MemberJoinRequest;
import com.or1is1.hometender.api.member.dto.request.MemberLoginRequest;
import com.or1is1.hometender.api.member.dto.request.MemberWithdrawRequest;
import com.or1is1.hometender.api.member.dto.response.MemberIsExistsResponse;
import com.or1is1.hometender.api.member.dto.MemberLoginResult;
import com.or1is1.hometender.api.member.exception.MemberCanNotFindException;
import com.or1is1.hometender.api.member.repository.MemberRepository;
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
	public void join(MemberJoinRequest userSignUpRequest) {
		String loginId = userSignUpRequest.loginId();
		String password = passwordEncoder.encode(userSignUpRequest.password());
		String nickname = userSignUpRequest.nickname();

		memberRepository.save(new Member(loginId, password, nickname));
	}

	public MemberLoginResult login(MemberLoginRequest memberLoginRequest) {
		String loginId = memberLoginRequest.loginId();
		String password = memberLoginRequest.password();
		MemberCanNotFindException memberCanNotFindException = new MemberCanNotFindException();

		Member member = memberRepository.findByLoginId(loginId)
				.orElseThrow(() -> memberCanNotFindException);

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw memberCanNotFindException;
		}

		return new MemberLoginResult(member);
	}

	public MemberIsExistsResponse isExists(MemberExistsRequest memberExistsRequest) {
		return memberRepository.isExists(memberExistsRequest.loginId(), memberExistsRequest.nickname());
	}

	@Transactional
	public Void withdraw(String loginId, MemberWithdrawRequest memberWithdrawRequest) {
		String password = memberWithdrawRequest.password();
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
