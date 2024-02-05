package com.or1is1.bartending.api.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional(readOnly = true)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입")
    @Transactional
    void join() {
        // given
        String email = "gildong@gmail.com";
        String password = "hong1443";
        String nickname = "홍길동";

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, password, nickname);

        // when
        MemberJoinResponse userSignResponse = memberService.join(memberJoinRequest);

        //then
        assertThat(userSignResponse.nickname()).isEqualTo(nickname);
    }
}
