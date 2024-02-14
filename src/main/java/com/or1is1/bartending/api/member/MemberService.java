package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.member.dto.*;
import com.or1is1.bartending.api.member.exception.MemberCanNotFindException;
import com.or1is1.bartending.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Locale.KOREAN;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MessageSource messageSource;

	@Transactional
	public MemberJoinResult join(MemberJoinRequest userSignUpRequest) {
		String loginId = userSignUpRequest.loginId();
		String password = passwordEncoder.encode(userSignUpRequest.password());
		String nickname = userSignUpRequest.nickname();

		Member savedMember = memberRepository.save(new Member(loginId, password, nickname));

		return new MemberJoinResult(savedMember);
	}

	public MemberLoginResult login(MemberLoginRequest memberLoginRequest) {
		String loginId = memberLoginRequest.loginId();
		String password = memberLoginRequest.password();

		String message = messageSource.getMessage("member.login.fail", null, KOREAN);
		MemberCanNotFindException memberCanNotFindException = new MemberCanNotFindException(message);

		Member member = memberRepository.findByLoginId(loginId)
				.orElseThrow(() -> memberCanNotFindException);

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw memberCanNotFindException;
		}

		return new MemberLoginResult(member);
	}

	public MemberIsExistsResult isExists(MemberExistsRequest memberExistsRequest) {
		return memberRepository.isExists(memberExistsRequest.loginId(), memberExistsRequest.nickname());
	}

	@Transactional
	public Void withdraw(String loginId, MemberWithdrawRequest memberWithdrawRequest) {
		String password = memberWithdrawRequest.password();
		MemberCanNotFindException memberCanNotFindException = new MemberCanNotFindException(messageSource.getMessage("member.withdraw.fail", null, KOREAN));

		Member member = memberRepository.findByLoginId(loginId)
				.orElseThrow(() -> memberCanNotFindException);

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw memberCanNotFindException;
		}

		memberRepository.delete(member);

		return null;
	}
}
