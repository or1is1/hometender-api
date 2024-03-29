package com.or1is1.hometender.api.domain.member.repository;

import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.dto.IsExistMemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	void isExists() {
		// given
		Member member = new Member(loginId, password, nickname);
		String loginId2 = "loginId2";
		String nickname2 = "nickname2";

		memberRepository.save(member);

		// when
		IsExistMemberResponse isExistMemberResponse1 = memberRepository.isExists(loginId, nickname);
		IsExistMemberResponse isExistMemberResponse2 = memberRepository.isExists(loginId, nickname2);
		IsExistMemberResponse isExistMemberResponse3 = memberRepository.isExists(loginId2, nickname);
		IsExistMemberResponse isExistMemberResponse4 = memberRepository.isExists(loginId2, nickname2);

		// then
		assertThat(isExistMemberResponse1.loginId()).isEqualTo(true);
		assertThat(isExistMemberResponse1.nickname()).isEqualTo(true);

		assertThat(isExistMemberResponse2.loginId()).isEqualTo(true);
		assertThat(isExistMemberResponse2.nickname()).isEqualTo(false);

		assertThat(isExistMemberResponse3.loginId()).isEqualTo(false);
		assertThat(isExistMemberResponse3.nickname()).isEqualTo(true);

		assertThat(isExistMemberResponse4).isNull();
	}
}
