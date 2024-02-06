package com.or1is1.bartending.api.member;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;


    // 공통 사용 요소 초기화
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String email = "gildong@gmail.com";
    private final String password = "hong1443";
    private final String nickname = "홍길동";
    private final String url = "/api/members";

    @Test
    @DisplayName("회원가입 정상처리")
    void join() throws Exception {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, password, nickname);
        String content = objectMapper.writeValueAsString(memberJoinRequest);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(APPLICATION_JSON)
                .content(content));

        //then
        result.andExpectAll(
                content().contentType(APPLICATION_JSON),
                status().isOk(),
                jsonPath("$.nickname").value(nickname)
        );
    }

    @Test
    @DisplayName("잘못된 이메일 형식으로 회원가입 시도")
    void joinWithWrongEmail() throws Exception {
        // given
        String email = "gildong#gmail.com";

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, password, nickname);
        String content = objectMapper.writeValueAsString(memberJoinRequest);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(APPLICATION_JSON)
                .content(content));

        //then
        result.andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    @DisplayName("너무 짧은 닉네임으로 회원가입 시도")
    void joinWithTooShortNickname() throws Exception {
        // given
        String nickname = "홍";

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, password, nickname);
        String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                .contentType(APPLICATION_JSON)
                .content(content));

        //then
        result.andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    @DisplayName("너무 긴 닉네임으로 회원가입 시도")
    void joinWithTooLongNickname() throws Exception {
        // given
        String nickname = "홍길동홍길동홍길동홍길동홍길동홍길동홍길동홍길동";

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, password, nickname);
        String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                .contentType(APPLICATION_JSON)
                .content(content));

        //then
        result.andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    @DisplayName("공백 닉네임으로 회원가입 시도")
    void joinWithEmptyNickname() throws Exception {
        // given
        String nickname = "      ";

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, password, nickname);
        String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                .contentType(APPLICATION_JSON)
                .content(content));

        //then
        result.andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    @DisplayName("너무 짧은 비밀번호로 회원가입 시도")
    void joinWithTooShortPassword() throws Exception {
        // given
        String password = "ho3";

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, password, nickname);
        String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                .contentType(APPLICATION_JSON)
                .content(content));

        //then
        result.andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    @DisplayName("너무 긴 비밀번호로 회원가입 시도")
    void joinWithTooLongPassword() throws Exception {
        // given
        String password = "hong1443hong1443hong1443hong1443";

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, password, nickname);
        String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                .contentType(APPLICATION_JSON)
                .content(content));

        //then
        result.andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    @DisplayName("공백 비밀번호로 회원가입 시도")
    void joinWithEmptyPassword() throws Exception {
        // given
        String password = "      ";

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(email, password, nickname);
        String content = new ObjectMapper().writeValueAsString(memberJoinRequest);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                .contentType(APPLICATION_JSON)
                .content(content));

        //then
        result.andExpectAll(
                status().isBadRequest()
        );
    }
}
