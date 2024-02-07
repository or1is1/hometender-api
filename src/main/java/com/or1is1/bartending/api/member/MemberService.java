package com.or1is1.bartending.api.member;

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
    public MemberJoinResponse join(MemberJoinRequest userSignUpRequest) {
        String loginId = userSignUpRequest.loginId();
        String password = passwordEncoder.encode(userSignUpRequest.password());
        String nickname = userSignUpRequest.nickname();

        Member savedMember = memberRepository.save(new Member(loginId, password, nickname));

        return new MemberJoinResponse(savedMember);
    }
}
