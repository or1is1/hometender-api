package com.or1is1.bartending.api.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional(readOnly = true)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입")
    @Transactional
    void join() {
        // given
        String loginId = "hong1443";
        String password = "hong1443@";
        String nickname = "홍길동";

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);

        // when
        MemberJoinResponse userSignResponse = memberService.join(memberJoinRequest);

        //then
        assertThat(userSignResponse.nickname()).isEqualTo(nickname);
    }
}
