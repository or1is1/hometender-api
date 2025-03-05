package com.or1is1.hometender.api.domain.member;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.or1is1.hometender.api.dto.PostMemberRequest;
import com.or1is1.hometender.api.dto.LoginMemberRequest;
import com.or1is1.hometender.api.dto.DeleteMemberRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.or1is1.hometender.api.common.StringConst.LOGIN_MEMBER;
import static java.util.Locale.KOREAN;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberIntegrationTest {
	private final String loginId;
	private final String password;
	private final String nickname;
	private final String url;

	@Autowired
	MockMvc mockMvc;
	@Autowired
	MessageSource messageSource;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MemberService memberService;

	private MockHttpSession mockHttpSession;

	public MemberIntegrationTest() {
		loginId = "loginId";
		password = "password";
		nickname = "nickname";
		url = "/api/members";
	}

	@BeforeEach
	public void beforeEach() {
		mockHttpSession = new MockHttpSession();
	}

	@AfterEach
	public void afterEach() {
		mockHttpSession = null;
	}

	@Test
	@DisplayName("회원가입")
	void join() throws Exception {
		// given
		PostMemberRequest postMemberRequest = new PostMemberRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(postMemberRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.nickname").value(nickname)
		);
	}

	@Test
	@DisplayName("로그인")
	void login() throws Exception {
		// given
		join();

		LoginMemberRequest loginMemberRequest = new LoginMemberRequest(loginId, password);
		String content = objectMapper.writeValueAsString(loginMemberRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/login")
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.nickname").value(nickname)
		);
	}

	@Test
	@DisplayName("로그인 실패 - 회원 정보 불일치")
	void loginFail() throws Exception {
		// given
		join();

		LoginMemberRequest loginMemberRequest = new LoginMemberRequest("loginId2", password);
		String content = objectMapper.writeValueAsString(loginMemberRequest);
		String message = messageSource.getMessage("exception.member.canNotFound", null, KOREAN);

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/login")
				.contentType(APPLICATION_JSON)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.message").value(message)
		);
	}

	@Test
	@DisplayName("로그아웃")
	void logout() throws Exception {
		// given
		join();

		Member member = new Member(loginId, password, nickname);
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LOGIN_MEMBER, member);

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/logout")
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession));

		// then
		resultActions.andExpectAll(
				status().isOk()
		);
	}

	@Test
	@DisplayName("로그아웃 실패 - 기존 로그인 회원 없음")
	void logoutFail() throws Exception {
		// given
		String message = messageSource.getMessage("exception.member.needToLogin", null, KOREAN);

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/logout")
				.contentType(APPLICATION_JSON));

		// then
		resultActions.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.message").value(message)
		);
	}

	@Test
	@DisplayName("회원 탈퇴")
	void withdraw() throws Exception {
		// given
		join();

		DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest(password);
		String content = objectMapper.writeValueAsString(deleteMemberRequest);

		// when
		ResultActions resultActions = mockMvc.perform(delete(url)
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isOk()
		);
	}

	@Test
	@DisplayName("회원 탈퇴 실패 - 회원 정보 불일치")
	void withdrawFail() throws Exception {
		// given
		join();

		String wrongPassword = "wrongPassword";
		DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest(wrongPassword);
		String content = objectMapper.writeValueAsString(deleteMemberRequest);
		String message = messageSource.getMessage("exception.member.canNotFound", null, KOREAN);

		// when
		ResultActions resultActions = mockMvc.perform(delete(url)
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.message").value(message)
		);
	}
}
