package com.or1is1.bartending.api.member;

import com.or1is1.bartending.api.member.dto.MemberJoinRequest;
import com.or1is1.bartending.api.member.dto.MemberJoinResult;
import com.or1is1.bartending.api.member.dto.MemberLoginRequest;
import com.or1is1.bartending.api.member.dto.MemberLoginResult;
import com.or1is1.bartending.api.member.exception.MemberAlreadyExistsException;
import com.or1is1.bartending.api.member.exception.MemberCanNotFindException;
import com.or1is1.bartending.api.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional(readOnly = true)
class MemberServiceTest {
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private PasswordEncoder mockPasswordEncoder;
	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final String loginId;
	private final String password;
	private final String nickname;

	public MemberServiceTest() {
		loginId = "loginId";
		password = "password!";
		nickname = "nickname";
	}

	@Test
	@DisplayName("회원가입 성공")
	@Transactional
	void join() {
		// given
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		Member member = new Member(loginId, passwordEncoder.encode(password), nickname);

		given(memberRepository.save(any(Member.class)))
				.willReturn(member);

		// when
		MemberJoinResult memberJoinResult = memberService.join(memberJoinRequest);

		// then
		assertThat(memberJoinResult.nickname()).isEqualTo(nickname);
	}

	@Test
	@DisplayName("회원가입 실패 - 중복")
	@Transactional
	void JoinWithAlreadyExists() {
		// given
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		Member member = new Member(loginId, passwordEncoder.encode(password), nickname);

		given(memberRepository.save(any(Member.class)))
				.willThrow(MemberAlreadyExistsException.class);

		// when then
		assertThatThrownBy(() -> memberService.join(memberJoinRequest))
				.isInstanceOf(MemberAlreadyExistsException.class);
	}

	@Test
	@DisplayName("로그인 성공")
	@Transactional
	void login() {
		// given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(loginId, password);
		String encodedPassword = passwordEncoder.encode(password);
		Member member = new Member(loginId, encodedPassword, nickname);

		given(memberRepository.findByLoginId(loginId))
				.willReturn(of(member));
		given(mockPasswordEncoder.matches(password, encodedPassword))
				.willReturn(true);

		// when
		MemberLoginResult memberLoginResult = memberService.login(memberLoginRequest);

		// then
		assertThat(memberLoginResult.nickname()).isEqualTo(nickname);
	}

	@Test
	@DisplayName("로그인 실패 - 회원 정보 없음")
	@Transactional
	void loginFailWithMemberIsNotExists() {
		// given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(loginId, password);
		String encodedPassword = passwordEncoder.encode(password);
		Member member = new Member(loginId, encodedPassword, nickname);

		given(memberRepository.findByLoginId(loginId))
				.willReturn(empty());
		given(mockPasswordEncoder.matches(password, encodedPassword))
				.willReturn(true);

		// when then
		assertThatThrownBy(() -> memberService.login(memberLoginRequest))
				.isInstanceOf(MemberCanNotFindException.class);

		// then
		assertThatThrownBy(() -> memberService.login(memberLoginRequest))
				.isInstanceOf(MemberCanNotFindException.class);
	}

	@Test
	@DisplayName("로그인 실패 - 비밀번호가 틀림")
	@Transactional
	void loginFailWithWrongPassword() {
		// given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(loginId, password);
		String encodedPassword = passwordEncoder.encode(password);
		Member member = new Member(loginId, encodedPassword, nickname);

		given(memberRepository.findByLoginId(loginId))
				.willReturn(of(member));
		given(mockPasswordEncoder.matches(password, encodedPassword))
				.willReturn(false);

		// when then
		assertThatThrownBy(() -> memberService.login(memberLoginRequest))
				.isInstanceOf(MemberCanNotFindException.class);
	}
}
