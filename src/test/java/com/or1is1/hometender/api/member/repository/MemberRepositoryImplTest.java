package com.or1is1.hometender.api.member.repository;

import com.or1is1.hometender.api.member.Member;
import com.or1is1.hometender.api.member.dto.response.MemberIsExistsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryImplTest {
	private final String loginId;
	private final String password;
	private final String nickname;

	@Autowired
	MemberRepository memberRepository;

	public MemberRepositoryImplTest() {
		loginId = "loginId";
		password = "password";
		nickname = "nickname";
	}

	@Test
	@DisplayName("회원 정보 중복")
	void isExists() {
		// given
		Member member = new Member(loginId, password, nickname);
		String loginId2 = "loginId2";
		String nickname2 = "nickname2";

		memberRepository.save(member);

		// when
		MemberIsExistsResponse memberIsExistsResponse1 = memberRepository.isExists(loginId, nickname);
		MemberIsExistsResponse memberIsExistsResponse2 = memberRepository.isExists(loginId, nickname2);
		MemberIsExistsResponse memberIsExistsResponse3 = memberRepository.isExists(loginId2, nickname);
		MemberIsExistsResponse memberIsExistsResponse4 = memberRepository.isExists(loginId2, nickname2);

		// then
		assertThat(memberIsExistsResponse1.loginId()).isEqualTo(true);
		assertThat(memberIsExistsResponse1.nickname()).isEqualTo(true);

		assertThat(memberIsExistsResponse2.loginId()).isEqualTo(true);
		assertThat(memberIsExistsResponse2.nickname()).isEqualTo(false);

		assertThat(memberIsExistsResponse3.loginId()).isEqualTo(false);
		assertThat(memberIsExistsResponse3.nickname()).isEqualTo(true);

		assertThat(memberIsExistsResponse4).isNull();
	}
}
