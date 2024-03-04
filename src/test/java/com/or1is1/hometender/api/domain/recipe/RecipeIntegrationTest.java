package com.or1is1.hometender.api.domain.recipe;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.or1is1.hometender.api.domain.ingredient.dto.IngredientDto;
import com.or1is1.hometender.api.domain.member.MemberService;
import com.or1is1.hometender.api.domain.member.dto.PostMemberRequest;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeDto;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeIngredientDto;
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

import java.util.ArrayList;
import java.util.List;

import static com.or1is1.hometender.api.domain.recipe.CraftMethod.BUILD;
import static com.or1is1.hometender.api.domain.recipe.SizeType.OZ;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeIntegrationTest {
	private final String loginId;
	private final String password;
	private final String nickname;
	private final String url;
	private final String joinUrl;

	@Autowired
	MockMvc mockMvc;
	@Autowired
	MessageSource messageSource;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MemberService MemberService;
	@Autowired
	MemberService RecipeService;

	private MockHttpSession mockHttpSession;

	public RecipeIntegrationTest() {
		loginId = "loginId";
		password = "password";
		nickname = "nickname";
		url = "/api/recipe";
		joinUrl = "/api/members/join";
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
	@DisplayName("레시피 수정")
	void postRecipe() throws Exception {
		// given
		PostMemberRequest postMemberRequest = new PostMemberRequest(loginId, password, nickname);
		String contentOfJOin = objectMapper.writeValueAsString(postMemberRequest);

		// 회원가입 & 로그인
		mockMvc.perform(post(joinUrl)
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(contentOfJOin));

		IngredientDto ingredient1 = new IngredientDto("스카치 위스키", "스코틀랜드의 위스키이다.", 40L);
		String content = objectMapper.writeValueAsString(ingredient1);

		// 재료 추가 1
		mockMvc.perform(post("/api/ingredients")
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(content));

		IngredientDto ingredient2 = new IngredientDto("드람뷔", "꿀처럼 단, 허브 리큐르이다.", 40L);
		content = objectMapper.writeValueAsString(ingredient2);

		// 재료 추가 2
		mockMvc.perform(post("/api/ingredients")
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(content));

		RecipeIngredientDto recipeIngredientDto1 = new RecipeIngredientDto(1L, ingredient1.name(), ingredient1.volume(), 2, OZ, false);
		RecipeIngredientDto recipeIngredientDto2 = new RecipeIngredientDto(2L, ingredient2.name(), ingredient2.volume(), 1, OZ, false);

		List<RecipeIngredientDto> recipeIngredientDtoList = new ArrayList<>();
		recipeIngredientDtoList.add(recipeIngredientDto1);
		recipeIngredientDtoList.add(recipeIngredientDto2);

		RecipeDto rustyNail = new RecipeDto("러스티 네일", "녹슨 못", BUILD, recipeIngredientDtoList, "맛있다.");
		content = objectMapper.writeValueAsString(rustyNail);

		// 레시피 추가
		mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(content));

		IngredientDto ingredient3 = new IngredientDto("디사론노", "아몬드 리큐르이다.", 28L);
		content = objectMapper.writeValueAsString(ingredient3);

		// 재료 추가 3
		mockMvc.perform(post("/api/ingredients")
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(content));

		RecipeIngredientDto recipeIngredientDto3 = new RecipeIngredientDto(3L, ingredient3.name(), ingredient3.volume(), 2, OZ, false);

		recipeIngredientDtoList.clear();
		recipeIngredientDtoList.add(recipeIngredientDto1);
		recipeIngredientDtoList.add(recipeIngredientDto3);

		RecipeDto godFather = new RecipeDto("갓 파더", "영화 대부", BUILD, recipeIngredientDtoList, "맛있다.");
		content = objectMapper.writeValueAsString(godFather);

		// when
		ResultActions resultActions = mockMvc.perform(put(url + "/1")
				.contentType(APPLICATION_JSON)
				.session(mockHttpSession)
				.content(content));

		//then
		resultActions.andExpectAll(
				status().isOk()
		);
	}
}
