package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.dto.GetRecipeListResponse;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeDto;
import com.or1is1.hometender.api.domain.recipe.repository.RecipeIngredientRepository;
import com.or1is1.hometender.api.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.or1is1.hometender.api.common.DomainException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeIngredientRepository recipeIngredientRepository;

	@Transactional
	public void post(Long loginId, RecipeDto recipeDto) {

		Recipe recipe = new Recipe(
				new Member(loginId),
				recipeDto.name(),
				recipeDto.description(),
				recipeDto.craftMethod(),
				recipeDto.recipeIngredientList(),
				recipeDto.manual()
		);

		recipeRepository.save(recipe);
	}

	public List<GetRecipeListResponse> getList(Long loginId) {

		return recipeRepository.findByWriter(new Member(loginId))
				.stream().map(GetRecipeListResponse::new)
				.toList();
	}

	public RecipeDto get(Long recipeId, Long loginId) {

		Recipe recipe = recipeRepository.findByRecipeIdAndWriter(recipeId, new Member(loginId))
				.orElseThrow(() -> INGREDIENT_CAN_NOT_FIND_EXCEPTION);

		return new RecipeDto(recipe);
	}

	@Transactional
	public void put(Long recipeId, Long loginId, RecipeDto recipeDto) {

		Recipe recipe = recipeRepository.findByRecipeIdAndWriter(recipeId, new Member(loginId))
				.orElseThrow(() -> RECIPE_CAN_NOT_FIND_EXCEPTION);

		if (!loginId.equals(recipe.getWriter().getId())) {
			throw RECIPE_IS_NOT_MINE_EXCEPTION;
		}

		recipeIngredientRepository.deleteByRecipe(recipe);

		recipe.put(
				recipeDto.name(),
				recipeDto.description(),
				recipeDto.craftMethod(),
				recipeDto.recipeIngredientList(),
				recipeDto.manual()
		);
	}

	@Transactional
	public void delete(Long recipeId, Long loginId) {
		recipeRepository.deleteByRecipeIdAndWriter(recipeId, new Member(loginId));
	}
}
