package com.or1is1.hometender.api.member;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.or1is1.hometender.api.member.dto.request.MemberJoinRequest;
import com.or1is1.hometender.api.member.dto.request.MemberLoginRequest;
import com.or1is1.hometender.api.member.dto.request.MemberWithdrawRequest;
import com.or1is1.hometender.api.member.dto.MemberLoginResult;
import com.or1is1.hometender.api.member.exception.MemberCanNotFindException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.or1is1.hometender.api.StringConst.LOGIN_MEMBER;
import static java.util.Locale.KOREAN;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {MemberController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class MemberControllerTest {
	private final String loginId;
	private final String password;
	private final String nickname;
	private final String url;

	@MockBean
	private MemberService memberService;

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	public MemberControllerTest() {
		loginId = "loginId";
		password = "password!";
		nickname = "nickname";
		url = "/api/members";
	}

	@Test
	@DisplayName("회원가입")
	void join() throws Exception {
		// given
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(loginId, password);
		MemberLoginResult memberLoginResult = new MemberLoginResult(1L, nickname);

		given(memberService.login(memberLoginRequest))
				.willReturn(memberLoginResult);

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/join")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.data.nickname").value(nickname)
		);

		verify(memberService).join(memberJoinRequest);
	}

	@Test
	@DisplayName("로그인")
	void login() throws Exception {
		// given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(loginId, password);
		String content = objectMapper.writeValueAsString(memberLoginRequest);

		MemberLoginResult memberLoginResult = new MemberLoginResult(1L, nickname);

		given(memberService.login(memberLoginRequest))
				.willReturn(memberLoginResult);

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/login")
				.contentType(APPLICATION_JSON)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.data.nickname").value(nickname)
		);

		verify(memberService).login(memberLoginRequest);
	}

	@Test
	@DisplayName("로그인 실패 - 회원 정보 불일치")
	void loginFail() throws Exception {
		// given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(loginId, password);
		String content = objectMapper.writeValueAsString(memberLoginRequest);
		String message = messageSource.getMessage("member.exception.canNotFound", null, KOREAN);

		given(memberService.login(memberLoginRequest))
				.willThrow(new MemberCanNotFindException());

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/login")
				.contentType(APPLICATION_JSON)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.message").value(message)
		);

		verify(memberService).login(memberLoginRequest);
	}

	@Test
	@DisplayName("로그아웃")
	void logout() throws Exception {
		// given
		Member member = new Member(loginId, password, nickname);
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LOGIN_MEMBER, member);

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/logout")
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession));

		// then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.data").value(true)
		);
	}

	@Test
	@DisplayName("로그아웃 실패 - 기존 로그인 회원 없음")
	void logoutFail() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/logout")
				.contentType(APPLICATION_JSON));

		// then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.data").value(false)
		);
	}

	@Test
	@DisplayName("회원 탈퇴")
	void withdraw() throws Exception {
		// given
		MemberWithdrawRequest memberWithdrawRequest = new MemberWithdrawRequest(password);
		String content = objectMapper.writeValueAsString(memberWithdrawRequest);

		doNothing().when(memberService).withdraw(loginId, memberWithdrawRequest);

		// when
		ResultActions resultActions = mockMvc.perform(delete(url + "/" + loginId)
				.contentType(APPLICATION_JSON)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isOk()
		);

		verify(memberService).withdraw(loginId, memberWithdrawRequest);
	}

	@Test
	@DisplayName("회원 탈퇴 실패 - 회원 정보 불일치")
	void withdrawFail() throws Exception {
		// given
		MemberWithdrawRequest memberWithdrawRequest = new MemberWithdrawRequest(password);
		String content = objectMapper.writeValueAsString(memberWithdrawRequest);
		String message = messageSource.getMessage("member.exception.canNotFound", null, KOREAN);

		given(memberService.withdraw(loginId, memberWithdrawRequest))
				.willThrow(new MemberCanNotFindException());

		// when
		ResultActions resultActions = mockMvc.perform(delete(url + "/" + loginId)
				.contentType(APPLICATION_JSON)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.message").value(message)
		);

		verify(memberService).withdraw(loginId, memberWithdrawRequest);
	}
}
