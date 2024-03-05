package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.domain.ingredient.dto.IngredientDto;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientIsNotMineException;
import com.or1is1.hometender.api.domain.ingredient.repository.IngredientRepository;
import com.or1is1.hometender.api.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {
	@Mock
	private IngredientRepository ingredientRepository;

	@InjectMocks
	private IngredientService ingredientService;

	@Test
	@DisplayName("재료 조회 - 재료를 못찾으면 예외 발생")
	void get() {
		// given
		given(ingredientRepository.findByIngredientIdAndWriter(anyLong(), any(Member.class)))
				.willThrow(IngredientCanNotFindException.class);

		// when then
		assertThatThrownBy(() -> ingredientService.get(1L, 1L))
				.isExactlyInstanceOf(IngredientCanNotFindException.class);
	}

	@Test
	@DisplayName("재료 조회 - 재료의 소유자가 다르면 예외 발생")
	void put() {
		// given
		long memberId = 1L;
		Member member = new Member(memberId);
		Ingredient ingredient = new Ingredient(member, "레시피명", "설명", 0.f);

		given(ingredientRepository.findByIngredientIdAndWriter(anyLong(), any(Member.class)))
				.willReturn(Optional.of(ingredient));

		IngredientDto ingredientDto = new IngredientDto(ingredient);

		// when then
		long loginId = 2L;
		assertThatThrownBy(() -> ingredientService.put(1L, loginId, ingredientDto))
				.isExactlyInstanceOf(IngredientIsNotMineException.class);
	}
}
