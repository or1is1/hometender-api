package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.member.dto.MemberExistsRequest;
import com.or1is1.bartending.api.member.dto.MemberExistsResult;
import com.or1is1.bartending.api.member.dto.MemberJoinRequest;
import com.or1is1.bartending.api.member.dto.MemberJoinResult;
import com.or1is1.bartending.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public MemberJoinResult join(MemberJoinRequest userSignUpRequest) {
		String loginId = userSignUpRequest.loginId();
		String password = passwordEncoder.encode(userSignUpRequest.password());
		String nickname = userSignUpRequest.nickname();

		Member savedMember = memberRepository.save(new Member(loginId, password, nickname));

		return new MemberJoinResult(savedMember);
	}

	public MemberExistsResult isExists(MemberExistsRequest memberExistsRequest) {
		return memberRepository.isExists(memberExistsRequest.loginId(), memberExistsRequest.nickname());
	}
}
