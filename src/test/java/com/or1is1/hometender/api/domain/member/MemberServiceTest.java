package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.domain.member.dto.LoginMemberResult;
import com.or1is1.hometender.api.domain.member.dto.PostMemberRequest;
import com.or1is1.hometender.api.domain.member.dto.LoginMemberRequest;
import com.or1is1.hometender.api.domain.member.dto.DeleteMemberRequest;
import com.or1is1.hometender.api.domain.member.exception.MemberAlreadyExistsException;
import com.or1is1.hometender.api.domain.member.exception.MemberCanNotFindException;
import com.or1is1.hometender.api.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
	private final String loginId;
	private final String password;
	private final String nickname;

	@Mock
	private MemberRepository memberRepository;
	@Mock
	private PasswordEncoder mockPasswordEncoder;

	@InjectMocks
	private MemberService memberService;

	public MemberServiceTest() {
		loginId = "loginId";
		password = "password";
		nickname = "nickname";
	}

	@Test
	@DisplayName("회원가입")
	void join() {
		// given
		PostMemberRequest postMemberRequest = new PostMemberRequest(loginId, password, nickname);

		// when
		memberService.join(postMemberRequest);

		// then
		verify(memberRepository).save(any(Member.class));
	}

	@Test
	@DisplayName("회원가입 실패 - 중복")
	void JoinWithAlreadyExists() {
		// given
		PostMemberRequest postMemberRequest = new PostMemberRequest(loginId, password, nickname);

		given(memberRepository.save(any(Member.class)))
				.willThrow(MemberAlreadyExistsException.class);

		// when then
		assertThatThrownBy(() -> memberService.join(postMemberRequest))
				.isInstanceOf(MemberAlreadyExistsException.class);
	}

	@Test
	@DisplayName("로그인")
	void login() {
		// given
		LoginMemberRequest loginMemberRequest = new LoginMemberRequest(loginId, password);
		Member member = new Member(loginId, password, nickname);

		given(memberRepository.findByLoginId(loginId))
				.willReturn(of(member));
		given(mockPasswordEncoder.matches(password, password))
				.willReturn(true);

		// when
		LoginMemberResult loginMemberResult = memberService.login(loginMemberRequest);

		// then
		assertThat(loginMemberResult.nickname()).isEqualTo(nickname);
	}

	@Test
	@DisplayName("로그인 실패 - 회원 정보 없음")
	void loginFailWithMemberIsNotExists() {
		// given
		LoginMemberRequest loginMemberRequest = new LoginMemberRequest(loginId, password);

		given(memberRepository.findByLoginId(loginId))
				.willReturn(empty());

		// when then
		assertThatThrownBy(() -> memberService.login(loginMemberRequest))
				.isInstanceOf(MemberCanNotFindException.class);
	}

	@Test
	@DisplayName("로그인 실패 - 비밀번호가 틀림")
	void loginFailWithWrongPassword() {
		// given
		LoginMemberRequest loginMemberRequest = new LoginMemberRequest(loginId, password);
		Member member = new Member(loginId, password, nickname);

		given(memberRepository.findByLoginId(loginId))
				.willReturn(of(member));
		given(mockPasswordEncoder.matches(password, password))
				.willReturn(false);

		// when then
		assertThatThrownBy(() -> memberService.login(loginMemberRequest))
				.isInstanceOf(MemberCanNotFindException.class);
	}

	@Test
	@DisplayName("회원탈퇴")
	void withdraw() {
		// given
		DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest(password);
		Member member = new Member(loginId, password, nickname);

		given(memberRepository.findByLoginId(loginId))
				.willReturn(of(member));

		given(mockPasswordEncoder.matches(password, password))
				.willReturn(true);

		// when
		memberService.withdraw(loginId, deleteMemberRequest);

		// then
		verify(memberRepository).delete(member);
	}

	@Test
	@DisplayName("회원탈퇴 실패 - 비밀번호가 틀림")
	void withdrawWithWrongPassword() {
		// given
		DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest(password);
		Member member = new Member(loginId, password, nickname);

		given(memberRepository.findByLoginId(loginId))
				.willReturn(of(member));

		// when then
		assertThatThrownBy(() -> memberService.withdraw(loginId, deleteMemberRequest))
				.isExactlyInstanceOf(MemberCanNotFindException.class);
	}
}
