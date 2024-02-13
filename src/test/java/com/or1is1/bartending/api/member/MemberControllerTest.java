package com.or1is1.bartending.api.member;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.or1is1.bartending.api.member.dto.MemberJoinRequest;
import com.or1is1.bartending.api.member.dto.MemberJoinResult;
import com.or1is1.bartending.api.member.dto.MemberLoginRequest;
import com.or1is1.bartending.api.member.dto.MemberLoginResult;
import com.or1is1.bartending.api.member.exception.MemberCanNotFindException;
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

import static com.or1is1.bartending.api.SessionConst.LOGIN_MEMBER;
import static java.util.Locale.KOREAN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {MemberController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class MemberControllerTest {
	private final String loginId;
	private final String password;
	private final String nickname;
	private final String url;
	@Autowired
	MessageSource messageSource;
	@MockBean
	private MemberService memberService;
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
	@DisplayName("회원가입 성공")
	void join() throws Exception {
		// given
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		given(memberService.join(any(MemberJoinRequest.class)))
				.willReturn(new MemberJoinResult(1L, nickname));

		// when
		ResultActions resultActions = mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nickname").value(nickname));
	}

	@Test
	@DisplayName("로그인 아이디는 비어있으면 안된다.")
	void joinWithBlankLoginId() throws Exception {
		// given
		String loginId = "        ";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.data[0]")
								.value(messageSource.getMessage("validation.constraints.NotBlank", null, KOREAN))
				);
	}

	@Test
	@DisplayName("로그인 아이디는 5글자 이상이어야 한다.")
	void joinWithTooShortLoginId() throws Exception {
		// given
		String loginId = "gild";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.data[0]")
								.value(messageSource.getMessage("validation.constraints.Size.loginId", null, KOREAN))
				);
	}

	@Test
	@DisplayName("로그인 아이디는 20글자 미만이어야 한다.")
	void joinWithTooLongLoginId() throws Exception {
		// given
		String loginId = "hong1443hong1443hong1";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.data[0]")
								.value(messageSource.getMessage("validation.constraints.Size.loginId", null, KOREAN))
				);
	}

	@Test
	@DisplayName("비밀번호는 비어 있으면 안된다.")
	void joinWithEmptyPassword() throws Exception {
		// given
		String password = "            ";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.data[0]")
								.value(messageSource.getMessage("validation.constraints.NotBlank", null, KOREAN))
				);
	}

	@Test
	@DisplayName("비밀번호는 4글자보다 길어야 한다.")
	void joinWithTooShortPassword() throws Exception {
		// given
		String password = "hong144";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.data[0]")
								.value(messageSource.getMessage("validation.constraints.Size.password", null, KOREAN))
				);
	}

	@Test
	@DisplayName("비밀번호는 20자보다 짧아야 한다.")
	void joinWithTooLongPassword() throws Exception {
		// given
		String password = "hong1443hong1443hong14";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.data[0]")
								.value(messageSource.getMessage("validation.constraints.Size.password", null, KOREAN))
				);
	}

	@Test
	@DisplayName("닉네임은 비어있으면 안된다.")
	void joinWithEmptyNickname() throws Exception {
		// given
		String nickname = "      ";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.data[0]")
								.value(messageSource.getMessage("validation.constraints.NotBlank", null, KOREAN))
				);
	}

	@Test
	@DisplayName("닉네임의 길이는 2자보다 길어야 한다.")
	void joinWithTooShortNickname() throws Exception {
		// given
		String nickname = "홍";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.data[0]")
								.value(messageSource.getMessage("validation.constraints.Size.nickname", null, KOREAN))
				);
	}

	@Test
	@DisplayName("닉네임의 길이는 10자보다 짧아야 한다")
	void joinWithTooLongNickname() throws Exception {
		// given
		String nickname = "홍길동홍길동홍길동홍길";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.data[0]")
								.value(messageSource.getMessage("validation.constraints.Size.nickname", null, KOREAN))
				);
	}

	@Test
	@DisplayName("로그인 성공")
	void login() throws Exception {
		// given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(loginId, password);
		String content = objectMapper.writeValueAsString(memberLoginRequest);

		MemberLoginResult memberLoginResult = new MemberLoginResult(1L, nickname);

		given(memberService.login(memberLoginRequest))
				.willReturn(memberLoginResult);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members/login")
				.contentType(APPLICATION_JSON)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.data.id").value(1L),
				jsonPath("$.data.nickname").value(nickname)
		);
	}

	@Test
	@DisplayName("로그인 실패 - 회원 정보 불일치")
	void loginFail() throws Exception {
		// given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(loginId, password);
		String content = objectMapper.writeValueAsString(memberLoginRequest);

		MemberCanNotFindException memberCanNotFindException = new MemberCanNotFindException(messageSource.getMessage("member.login.fail", null, KOREAN));

		given(memberService.login(memberLoginRequest))
				.willThrow(memberCanNotFindException);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members/login")
				.contentType(APPLICATION_JSON)
				.content(content));

		// then
		resultActions.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.message").value(messageSource.getMessage("member.login.fail", null, KOREAN))
		);
	}

	@Test
	@DisplayName("로그아웃")
	void logout() throws Exception {
		// given
		Member member = new Member(loginId, password, nickname);
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LOGIN_MEMBER, member);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members/logout")
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession));

		// then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.data.isInvalidated").value(true)
		);

	}

	@Test
	@DisplayName("로그아웃 - 기존 로그인 회원 없음")
	void logoutWithoutMember() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members/logout")
				.contentType(APPLICATION_JSON));

		// then
		resultActions.andExpectAll(
				status().isOk(),
				jsonPath("$.data.isInvalidated").value(false)
		);

	}
}
