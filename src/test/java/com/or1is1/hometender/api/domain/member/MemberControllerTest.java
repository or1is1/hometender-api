package com.or1is1.hometender.api.domain.member;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.or1is1.hometender.api.domain.member.dto.DeleteMemberRequest;
import com.or1is1.hometender.api.domain.member.dto.LoginMemberRequest;
import com.or1is1.hometender.api.domain.member.dto.LoginMemberResult;
import com.or1is1.hometender.api.domain.member.dto.PostMemberRequest;
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
import static com.or1is1.hometender.api.domain.member.exception.MemberCanNotFindException.MEMBER_CAN_NOT_FIND_EXCEPTION;
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
		PostMemberRequest postMemberRequest = new PostMemberRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(postMemberRequest);

		LoginMemberRequest loginMemberRequest = new LoginMemberRequest(loginId, password);
		LoginMemberResult loginMemberResult = new LoginMemberResult(1L, nickname);

		given(memberService.login(loginMemberRequest))
				.willReturn(loginMemberResult);

		// when
		ResultActions resultActions = mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.nickname").value(nickname)
		);

		verify(memberService).post(postMemberRequest);
	}

	@Test
	@DisplayName("로그인")
	void login() throws Exception {
		// given
		LoginMemberRequest loginMemberRequest = new LoginMemberRequest(loginId, password);
		String content = objectMapper.writeValueAsString(loginMemberRequest);

		LoginMemberResult loginMemberResult = new LoginMemberResult(1L, nickname);

		given(memberService.login(loginMemberRequest))
				.willReturn(loginMemberResult);

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/login")
				.contentType(APPLICATION_JSON)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.nickname").value(nickname)
		);

		verify(memberService).login(loginMemberRequest);
	}

	@Test
	@DisplayName("로그인 실패 - 회원 정보 불일치")
	void loginFail() throws Exception {
		// given
		LoginMemberRequest loginMemberRequest = new LoginMemberRequest(loginId, password);
		String content = objectMapper.writeValueAsString(loginMemberRequest);
		String message = messageSource.getMessage("member.exception.canNotFound", null, KOREAN);

		given(memberService.login(loginMemberRequest))
				.willThrow(MEMBER_CAN_NOT_FIND_EXCEPTION);

		// when
		ResultActions resultActions = mockMvc.perform(post(url + "/login")
				.contentType(APPLICATION_JSON)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.message").value(message)
		);

		verify(memberService).login(loginMemberRequest);
	}

	@Test
	@DisplayName("로그아웃")
	void logout() throws Exception {
		// given
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LOGIN_MEMBER, 1L);

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
	@DisplayName("회원 탈퇴")
	void withdraw() throws Exception {
		// given
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LOGIN_MEMBER, 1L);

		DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest(password);
		String content = objectMapper.writeValueAsString(deleteMemberRequest);

		doNothing().when(memberService)
				.delete(1L, deleteMemberRequest);

		// when
		ResultActions resultActions = mockMvc.perform(delete(url)
				.contentType(APPLICATION_JSON)
				.content(content)
				.session(mockHttpSession));

		// then
		resultActions.andExpectAll(
				status().isOk()
		);

		verify(memberService).delete(1L, deleteMemberRequest);
	}

	@Test
	@DisplayName("회원 탈퇴 실패 - 회원 정보 불일치")
	void withdrawFail() throws Exception {
		// given
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LOGIN_MEMBER, 1L);

		DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest(password);
		String content = objectMapper.writeValueAsString(deleteMemberRequest);
		String message = messageSource.getMessage("member.exception.canNotFound", null, KOREAN);

		given(memberService.delete(1L, deleteMemberRequest))
				.willThrow(MEMBER_CAN_NOT_FIND_EXCEPTION);

		// when
		ResultActions resultActions = mockMvc.perform(delete(url)
				.contentType(APPLICATION_JSON)
				.content(content)
				.session(mockHttpSession));

		// then
		resultActions.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.message").value(message)
		);

		verify(memberService).delete(1L, deleteMemberRequest);
	}
}
