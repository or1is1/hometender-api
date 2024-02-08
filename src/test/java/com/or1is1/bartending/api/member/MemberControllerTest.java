package com.or1is1.bartending.api.member;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {MemberController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class MemberControllerTest {
	private final ObjectMapper objectMapper;
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


	// 공통 사용 요소 초기화
	public MemberControllerTest() {
		objectMapper = new ObjectMapper();
		loginId = "hong1443";
		password = "hong1443!";
		nickname = "홍길동";
		url = "/api/members";
	}

	@Test
	@DisplayName("정상적인 회원가입 처리")
	void join() throws Exception {
		// given
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = objectMapper.writeValueAsString(memberJoinRequest);

		// when
		when(memberService.join(any(MemberJoinRequest.class)))
				.thenReturn(new MemberJoinResponse(1L, nickname));

		ResultActions resultActions = mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isOk())
				.andExpect(jsonPath("$.nickname").value(nickname));
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
						jsonPath("$.field").value("loginId"),
						jsonPath("$.code").value("NotBlank"),
						jsonPath("$.message")
								.value(messageSource.getMessage("jakarta.validation.constraints.NotBlank.message", null, null))
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
						jsonPath("$.field").value("loginId"),
						jsonPath("$.code").value("Size"),
						jsonPath("$.message")
								.value(messageSource.getMessage("validation.constraints.Size.loginId", null, null))
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
						jsonPath("$.field").value("loginId"),
						jsonPath("$.code").value("Size"),
						jsonPath("$.message")
								.value(messageSource.getMessage("validation.constraints.Size.loginId", null, null))
				);
	}

	@Test
	@DisplayName("비밀번호는 비어 있으면 안된다.")
	void joinWithEmptyPassword() throws Exception {
		// given
		String password = "            ";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.field").value("password"),
						jsonPath("$.code").value("NotBlank"),
						jsonPath("$.message")
								.value(messageSource.getMessage("jakarta.validation.constraints.NotBlank.message", null, null))
				);
	}

	@Test
	@DisplayName("비밀번호는 4글자보다 길어야 한다.")
	void joinWithTooShortPassword() throws Exception {
		// given
		String password = "hong144";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.field").value("password"),
						jsonPath("$.code").value("Size"),
						jsonPath("$.message")
								.value(messageSource.getMessage("validation.constraints.Size.password", null, null))
				);
	}

	@Test
	@DisplayName("비밀번호는 20자보다 짧아야 한다.")
	void joinWithTooLongPassword() throws Exception {
		// given
		String password = "hong1443hong1443hong14";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.field").value("password"),
						jsonPath("$.code").value("Size"),
						jsonPath("$.message")
								.value(messageSource.getMessage("validation.constraints.Size.password", null, null))
				);
	}

	@Test
	@DisplayName("닉네임은 비어있으면 안된다.")
	void joinWithEmptyNickname() throws Exception {
		// given
		String nickname = "      ";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.field").value("nickname"),
						jsonPath("$.code").value("NotBlank"),
						jsonPath("$.message")
								.value(messageSource.getMessage("jakarta.validation.constraints.NotBlank.message", null, null))
				);
	}

	@Test
	@DisplayName("닉네임의 길이는 2자보다 길어야 한다.")
	void joinWithTooShortNickname() throws Exception {
		// given
		String nickname = "홍";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//then
		resultActions.andExpect(status().isBadRequest())
				.andExpectAll(
						jsonPath("$.field").value("nickname"),
						jsonPath("$.code").value("Size"),
						jsonPath("$.message")
								.value(messageSource.getMessage("validation.constraints.Size.nickname", null, null))
				);
	}

	@Test
	@DisplayName("닉네임의 길이는 10자보다 짧아야 한다")
	void joinWithTooLongNickname() throws Exception {
		// given
		String nickname = "홍길동홍길동홍길동홍길";
		MemberJoinRequest memberJoinRequest = new MemberJoinRequest(loginId, password, nickname);
		String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/members")
				.contentType(APPLICATION_JSON)
				.content(content));

		//thenv
	}
}
