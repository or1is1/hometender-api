package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeDto;
import com.or1is1.hometender.api.domain.recipe.exception.RecipeCanNotFindException;
import com.or1is1.hometender.api.domain.recipe.exception.RecipeIsNotMineException;
import com.or1is1.hometender.api.domain.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.or1is1.hometender.api.domain.recipe.CraftMethod.BUILD;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
	@Mock
	private RecipeRepository recipeRepository;

	@InjectMocks
	private RecipeService recipeService;

	@Test
	@DisplayName("레시피 조회 - 레시피를 못찾으면 예외 발생")
	void get() {
		// given
		given(recipeRepository.findByRecipeIdAndWriter(anyLong(), any(Member.class)))
				.willThrow(RecipeCanNotFindException.class);

		// when then
		assertThatThrownBy(() -> recipeService.get(1L, 1L))
				.isExactlyInstanceOf(RecipeCanNotFindException.class);
	}

	@Test
	@DisplayName("레시피 조회 - 레시피의 소유자가 다르면 예외 발생")
	void put() {
		// given
		long memberId = 1L;
		Member member = new Member(memberId);
		Recipe recipe = new Recipe(member, "레시피명", "설명", BUILD, new ArrayList<>(), "구체적인 설명");

		given(recipeRepository.findByRecipeIdAndWriter(anyLong(), any(Member.class)))
				.willReturn(Optional.of(recipe));

		RecipeDto recipeDto = new RecipeDto(recipe);

		// when then
		long loginId = 2L;
		assertThatThrownBy(() -> recipeService.put(1L, loginId, recipeDto))
				.isExactlyInstanceOf(RecipeIsNotMineException.class);
	}
}
