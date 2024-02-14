package com.or1is1.bartending.api.member.repository;

import com.or1is1.bartending.api.member.Member;
import com.or1is1.bartending.api.member.dto.MemberIsExistsResult;
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
		MemberIsExistsResult memberIsExistsResult1 = memberRepository.isExists(loginId, nickname);
		MemberIsExistsResult memberIsExistsResult2 = memberRepository.isExists(loginId, nickname2);
		MemberIsExistsResult memberIsExistsResult3 = memberRepository.isExists(loginId2, nickname);
		MemberIsExistsResult memberIsExistsResult4 = memberRepository.isExists(loginId2, nickname2);

		// then
		assertThat(memberIsExistsResult1.loginId()).isEqualTo(true);
		assertThat(memberIsExistsResult1.nickname()).isEqualTo(true);

		assertThat(memberIsExistsResult2.loginId()).isEqualTo(true);
		assertThat(memberIsExistsResult2.nickname()).isEqualTo(false);

		assertThat(memberIsExistsResult3.loginId()).isEqualTo(false);
		assertThat(memberIsExistsResult3.nickname()).isEqualTo(true);

		assertThat(memberIsExistsResult4).isNull();
	}
}
